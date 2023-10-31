<template>
  <!-- 会员列表 -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="真实姓名" prop="realName">
        <el-input
          v-model="queryParams.realName"
          placeholder="请输入真实姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入用户手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="请选择">
          <el-option value="" label="全部"></el-option>
          <el-option value="1" label="收入"></el-option>
          <el-option value="2" label="支出"></el-option>
        </el-select>
      </el-form-item> -->
      <el-form-item>
        <el-checkbox v-model="queryParams.online" border :true-label="1" :false-label="0">在线会员</el-checkbox>
      </el-form-item>
      <el-form-item label="注册时间" prop="registerTime">
        <el-date-picker
          v-model="dateRange"
          style="width: 340px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetimerange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:user:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:user:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:user:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:user:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" width="65"/>
      <el-table-column label="用户名" align="center" prop="userName">
        <template slot-scope="scope">
          <div v-if="scope.row.other.online === 1" class="red-text">
            {{scope.row.userName}}
          </div>
          <div v-else>{{scope.row.userName}}</div>
        </template>
      </el-table-column>
      <el-table-column label="已抢单/触发单" align="center">
        <template slot-scope="scope">
          <div>
            {{scope.row.orderCount}} / {{scope.row.prepareCount}}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="昵称" align="center" prop="nickName" />
      <el-table-column label="真实姓名" align="center" prop="realName" />
      <el-table-column label="用户余额" align="center" prop="balance" />
      <el-table-column label="冻结金额" align="center" prop="freezeBalance" />
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="银行信息" align="center" prop="bankName" width="200">
        <template slot-scope="scope">
          <div>{{scope.row.bankName}}</div>
          <div>{{scope.row.bankNo}}</div>
          <div>{{scope.row.bankAddr}}</div>
        </template>
      </el-table-column>
      
      <el-table-column label="上级代理" align="center" prop="userAgent" width="120">
        <template slot-scope="scope">
          <label class="red-text cursorPointer" @click="handleUserAgent(scope.row.userAgent)">{{scope.row.userAgent}}</label>
          <el-button
            size="small"
            type="text"
            v-clipboard:copy="scope.row.userAgent"
            v-clipboard:success="onCopy"
          >复制</el-button>
          <div>{{scope.row.userAgentName}}</div>
        </template>
      </el-table-column>
      <el-table-column label="邀请码" align="center" prop="inviteCode" />
      <el-table-column align="center" width="180">
        <template slot="header">
          <div>注册时间</div>
          <div>上次登陆时间</div>
        </template>
        <template slot-scope="scope">
          <div>{{ scope.row.registerTime }}</div>
          <div>{{ scope.row.lastTime }}</div>
        </template>
      </el-table-column>
      <el-table-column align="center" width="180">
        <template slot="header">
          <div>注册IP</div>
          <div>上次登陆IP</div>
        </template>
        <template slot-scope="scope">
          <div>{{ scope.row.registerIp }}</div>
          <div>{{ scope.row.lastIp }}</div>
        </template>
      </el-table-column>
      <!-- <el-table-column label="登录密码" align="center" prop="loginPwd" /> -->
      <!-- <el-table-column label="支付密码" align="center" prop="payPwd" /> -->
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            @change="changeStatus(scope.row.id,scope.row.status,'user')"
            :active-value="0"
            :inactive-value="1"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="资金状态" align="center" prop="fundsStatus">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.fundsStatus"
            @change="changeStatus(scope.row.id,scope.row.fundsStatus,'zijin')"
            :active-value="0"
            :inactive-value="1"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <!-- <el-table-column label="信用分" align="center" prop="creditValue" /> -->
      <!-- <el-table-column label="用户等级" align="center" prop="levelId" /> -->
      <!-- <el-table-column label="是否累加订单(0:是 1:否)" align="center" prop="updateOrder" /> -->
      <el-table-column label="详情" align="center" width="200">
          <template slot-scope="scope">
            <div>总充值：{{ scope.row.deposit }}</div>
            <div>总提现：{{ scope.row.withdraw }}</div>
            <div>总收益：{{ scope.row.income }}</div>
            <div>总投注：{{ scope.row.bet }}</div>
            <div>下级人数：{{ scope.row.inviteCount }}</div>
          </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:user:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="balanceUpdate(scope.row)"
            v-hasPermi="['business:user:optMoney']"
          >余额重置</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleBalance(scope.row)"
            v-hasPermi="['business:user:optMoney']"
          >上下分</el-button>
          <el-button
            size="mini"
            type="text"
            business:deposit:list
            v-hasPermi="['business:deposit:list']"
            @click="goPages(scope.row,'deposit')"
          >充值记录</el-button>
          <el-button
            size="mini"
            type="text"
            v-hasPermi="['business:account:list']"
            @click="goPages(scope.row,'account')"
          >账变记录</el-button>
          <el-button
            size="mini"
            type="text"
            v-hasPermi="['business:withdraw:list']"
            @click="goPages(scope.row,'withdraw')"
          >提现记录</el-button>
          <el-button
            size="mini"
            type="text"
            v-hasPermi="['business:user:list']"
            @click="viewTeam(scope.row.id)"
          >查看团队</el-button>
          <el-button
            size="mini"
            type="text"
            v-hasPermi="['business:user:list']"
            @click="goPrepare(scope.row)"
          >预派送</el-button>
          <el-button
            size="mini"
            type="text"
            v-hasPermi="['business:user:edit']"
            @click="setOddNo(scope.row)"
          >设置单数</el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:user:remove']"
          >删除</el-button> -->
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改会员列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="用户名" prop="userName">
          <el-input :disabled="true" v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入昵称" />
        </el-form-item>
        <!-- <el-form-item label="用户余额" prop="balance">
          <el-input v-model="form.balance" placeholder="请输入用户余额" />
        </el-form-item> -->
        <el-form-item label="登录密码">
          <el-input v-model="form.loginPwd" placeholder="不输入表示不修改" />
        </el-form-item>
        <el-form-item label="支付密码">
          <el-input v-model="form.payPwd" placeholder="不输入表示不修改" />
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="form.realName" placeholder="请输入真实姓名" />
        </el-form-item>
        <el-form-item label="用户手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入用户手机号" />
        </el-form-item>
        <el-form-item label="开户行" prop="bankName">
          <el-input v-model="form.bankName" placeholder="请输入开户行" />
        </el-form-item>
        <el-form-item label="银行卡号" prop="bankNo">
          <el-input v-model="form.bankNo" placeholder="请输入银行卡号" />
        </el-form-item>
        <el-form-item label="开户行地址" prop="bankAddr">
          <el-input v-model="form.bankAddr" placeholder="请输入开户行地址" />
        </el-form-item>
        <el-form-item label="信用分" prop="creditValue">
          <el-input v-model="form.creditValue" placeholder="请输入信用分" />
        </el-form-item>
        <el-form-item label="用户等级" prop="levelId">
          <el-select v-model="form.levelId" placeholder="请选择">
            <el-option
                v-for="item in levelList"
                :key="item.id"
                :label="item.levelName"
                :value="item.id">
              </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="累加订单" prop="updateOrder">
          <el-select v-model="form.updateOrder" placeholder="请选择">
            <el-option label="是" :value="0"></el-option>
            <el-option label="否" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remake">
          <el-input v-model="form.remake" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 添加对话框 -->
    <el-dialog title="添加会员" :visible.sync="addOpen" width="600px" append-to-body>
      <el-form ref="addform" :model="addForm" :rules="rules" label-width="120px">
        <el-form-item label="代理用户名">
          <el-input v-model="addForm.userAgent" placeholder="请输入推荐人用户名" />
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="addForm.userName" placeholder="请输入4-12位数字或字母" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="addForm.nickName" placeholder="请输入4-12位数字或字母" />
        </el-form-item>
        <el-form-item label="密码" prop="loginPwd">
          <el-input v-model="addForm.loginPwd" placeholder="请输入6-12位数字或字母" />
        </el-form-item>
        <el-form-item label="支付密码" prop="payPwd">
          <el-input v-model="addForm.payPwd" placeholder="请输入6位数字" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addSub">确 定</el-button>
        <el-button @click="addCancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 重置余额对话框 -->
    <el-dialog title="重置余额" :visible.sync="resetBalanceStatus" width="500px" append-to-body>
      <el-form ref="resetBalanceForm" :model="resetBalanceForm" :rules="rules" label-width="120px">
        <el-form-item label="用户名">
          <el-input :disabled="true" v-model="resetBalanceForm.userName" placeholder="请输入4-12位数字或字母" />
        </el-form-item>
        <el-form-item label="修改后余额" prop="balance">
          <el-input v-model="resetBalanceForm.balance" placeholder="请输入金额" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="balanceUpdateSub">确 定</el-button>
        <el-button @click="resetBalanceStatus = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 增减余额对话框 -->
    <el-dialog title="增减余额" :visible.sync="balanceOpen" width="500px" append-to-body>
      <el-form ref="balanceform" :model="balanceForm" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="userName">
          <el-input :disabled="true" v-model="balanceForm.userName" placeholder="请输入4-12位数字或字母" />
        </el-form-item>
        <el-form-item label="金额" prop="money">
          <el-input v-model="balanceForm.money" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="增减类型" prop="type">
          <el-select v-model="balanceForm.type" placeholder="请选择上下分类型">
            <el-option label="上分" :value="1"></el-option>
            <el-option label="下分" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="修改理由" prop="remark">
          <el-input v-model="balanceForm.remark" placeholder="请填写备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="balanceSub">确 定</el-button>
        <el-button @click="balanceCancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看团队弹窗 -->
    <el-dialog title="团队信息" :visible.sync="detailsOpen" width="1200px" append-to-body>
      <el-tabs v-model="userform.userAgentLevel" type="card" @tab-click="viewTeam(userform.id)">
        <el-tab-pane label="一级会员" name="1"></el-tab-pane>
        <el-tab-pane label="二级会员" name="2"></el-tab-pane>
        <el-tab-pane label="三级会员" name="3"></el-tab-pane>
      </el-tabs>
      <el-table :data="detailList">
        <el-table-column label="用户ID" align="center" prop="id"/>
        <el-table-column label="用户名" align="center" prop="userName" />
        <el-table-column label="账户余额" align="center" prop="balance" />
        <el-table-column label="佣金" align="center" prop="income" />
        <el-table-column label="充值" align="center" prop="deposit" />
        <el-table-column label="提现" align="center" prop="withdraw" />
        <el-table-column label="上级会员" align="center" prop="userAgent" />
        <el-table-column label="直推人数" align="center" prop="inviteCount" />
        <el-table-column label="注册时间" align="center" width="180" prop="registerTime" />
      </el-table>
      <pagination
        v-show="detailsTotal>0"
        :total="detailsTotal"
        :page.sync="userform.pageNum"
        :limit.sync="userform.pageSize"
        @pagination="viewTeam(userform.id)"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="detailsOpen = false">确 定</el-button>
        <el-button @click="detailsOpen = false">取 消</el-button>
      </div>
    </el-dialog>
     <!-- 设置单数对话框 -->
     <el-dialog title="设置单数" :visible.sync="setOddStatus" width="500px" append-to-body>
      <el-form ref="setOddForm" :model="setOddForm" :rules="rules" label-width="120px">
        <el-form-item label="用户名">
          <el-input :disabled="true" v-model="setOddForm.userName" placeholder="请输入4-12位数字或字母" />
        </el-form-item>
        <el-form-item label="单数" prop="orderCount">
          <el-input-number v-model="setOddForm.orderCount" :min="1" placeholder="请输入金额" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="setOddSub">确 定</el-button>
        <el-button @click="setOddStatus = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser ,resetBalance, balanceUser,nodeUser ,setTodayCount} from "@/api/business/user";
import { listLevel } from "@/api/business/level";
import { dateFormat } from '@/utils/auth'

export default {
  name: "User",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 会员列表表格数据
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 推荐人数时间范围
      daterangeRegisterTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        status: null,
        realName: null,
        phone: null,
        status: '',
        online: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        loginPwd: [
          { required: true, message: "登录密码不能为空", trigger: "blur" }
        ],
        nickName: [
          { required: true, message: "昵称不能为空", trigger: "blur" }
        ],
        payPwd: [
          { required: true, message: "支付密码不能为空", trigger: "blur" }
        ],
      },
      // 时间
      dateRange:[],
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      // 是否显示添加弹出层
      addOpen: false,
      // 表单参数
      addForm: {},
      levelList: [],//等级列表
      resetBalanceStatus: false,//重置余额弹窗
      resetBalanceForm: {
        id: '',
        userName: '',
        balance: '',
      },//重置余额数据
      // 是否显示增减余额弹出层
      balanceOpen: false,
      // 增减余额表单数据
      balanceForm: {
        remark:'',
      },
      detailList:[],//详情数据
      detailsOpen: false,//上下级详情弹窗
      detailsTotal: 0,
      // 用户详情数据
      userform: {
        pageNum: 1,
        pageSize: 10,
        userAgentLevel: "1",
      },
      // 设置单数
      setOddStatus: false,
      setOddForm: {
        userName: '',
        orderCount: 1,
      }
    };
  },
  created() {
    // this.getDefaultTime()
    this.queryParams.userName = this.$route.query.userName
    if(this.queryParams.userName){
      this.getList()
    }else{
      this.getList();
    }
    this.getLevelList()
  },
  methods: {
    /** 查询会员列表列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeRegisterTime && '' != this.daterangeRegisterTime) {
        this.queryParams.params["beginRegisterTime"] = this.daterangeRegisterTime[0];
        this.queryParams.params["endRegisterTime"] = this.daterangeRegisterTime[1];
      }
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.userList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userName: null,
        nickName: null,
        loginPwd: null,
        payPwd: null,
        realName: null,
        phone: null,
        bankName: null,
        bankNo: null,
        bankAddr: null,
        creditValue: null,
        levelId: null,
        remake: null,
        // updateOrder: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.daterangeRegisterTime = [];
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加会员列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getUser(id).then(response => {
        this.form = response.data;
        this.form.loginPwd = ''
        this.form.payPwd = ''
        this.open = true;
        this.title = "修改会员列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      let obj = {
        id: this.form.id,
        userName: this.form.userName,
        nickName: this.form.nickName,
        loginPwd: this.form.loginPwd,
        payPwd: this.form.payPwd,
        realName: this.form.realName,
        phone: this.form.phone,
        bankName: this.form.bankName,
        bankNo: this.form.bankNo,
        bankAddr: this.form.bankAddr,
        creditValue: this.form.creditValue,
        levelId: this.form.levelId,
        remake: this.form.remake,
        updateOrder: this.form.updateOrder,
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateUser(obj).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUser(obj).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除会员列表编号为"' + ids + '"的数据项？').then(function() {
        return delUser(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    // 修改冻结状态
    changeStatus(id,status,type){
      let obj = {}
      if(type === 'user'){
        obj = {
          id: id,
          status : status
        }
      }else if(type === 'zijin'){
        obj = {
          id: id,
          fundsStatus : status
        }
      }
      
      updateUser(obj).then(response => {
        this.$modal.msgSuccess("修改成功");
      });
    },
    getDefaultTime() {
      let end = new Date();
      let start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      this.dateRange[0] = dateFormat("YYYY-mm-dd" , end) + ' 00:00:00'
      this.dateRange[1] = dateFormat("YYYY-mm-dd" , end) + ' 23:59:59'
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.addOpen = true;
    },
    addSub(){
      this.$refs["addform"].validate(valid => {
        if (valid) {
          addUser(this.addForm).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.addOpen = false;
            this.getList();
          });
        }
      });
    },
    // 添加取消按钮
    addCancel() {
      this.addOpen = false;
      this.adReset();
    },
    // 表单重置
    addReset() {
      this.form = {
        userName: null,
        userAgent: null,
        loginPwd: null,
        payPwd: null,
      };
      this.resetForm("addform");
    },
    handleUserAgent(username){
      if(username === 'root'){
        return false
      }
      this.queryParams.userName = username
      this.getList()
    },
    // 等级
    getLevelList() {
      listLevel({
        pageNum: 1,
        pageSize: 1000,
      }).then(response => {
        this.levelList = response.rows;
      });
    },
    // 余额重置
    balanceUpdate(row){
      this.resetBalanceStatus = true
      this.resetBalanceForm.id = row.id
      this.resetBalanceForm.userName = row.userName
      this.resetBalanceForm.balance = row.balance
    },
    // 提交重置余额
    balanceUpdateSub(){
      this.$refs["resetBalanceForm"].validate(valid => {
        if (valid) {
          resetBalance(this.setOddForm).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.resetBalanceStatus = false;
            this.getList();
          });
        }
      });
    },
    // 增减余额
    handleBalance(data){
      // this.balanceReset();
      this.balanceOpen = true;
      this.balanceForm.userName = data.userName
    },
    // 增减余额表单重置
    balanceCancel(){
      this.balanceOpen = false;
      // this.balanceReset();
    },
    // 增减余额
    balanceSub(){
      this.$refs["balanceform"].validate(valid => {
        if (valid) {
          balanceUser(this.balanceForm).then(response => {
            this.$modal.msgSuccess("操作成功");
            this.balanceOpen = false;
            this.getList();
          });
        }
      });
    },
    goPages(data,type){
      let typeName = ''
      if(type === 'account'){
        typeName = 'Account'
      }else if(type === 'deposit'){
        typeName = 'Deposit'
      }else if(type === 'withdraw'){
        typeName = 'Withdraw'
      }
      const obj = { path: '/trade/' + type, name: typeName };
      this.$tab.closePage(obj).then(() => {
        this.$router.push({path:'/trade/' + type ,query:{userName: data.userName}})
      })
      
    },
    // 查看团队
    viewTeam(id){
      this.userform["id"] = id
      nodeUser(this.userform).then(response => {
        this.detailList = response.rows;
        this.detailsTotal = response.total;
        this.detailsOpen = true
      });
    },
    goPrepare(data){
      const obj = { path: '/trade/prepare-edit/index' , name: 'prepareEdit' };
      this.$tab.closePage(obj).then(() => {
        this.$router.push({path:'/trade/prepare-edit/index' ,query:{userName: data.userName}})
      })
    },
     // 复制
     onCopy(){
      this.$modal.msgSuccess("复制成功");
    },
    // 设置单数
    setOddNo(row){
      this.setOddStatus = true
      this.setOddForm.userName = row.userName
    },
    // 提交设置单数
    setOddSub(){
      this.$refs["setOddForm"].validate(valid => {
        if (valid) {
          setTodayCount(this.setOddForm).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.setOddStatus = false;
            this.getList();
          });
        }
      });
    },
  }
};
</script>
