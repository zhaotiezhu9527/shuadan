<template>
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
      <el-form-item label="昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入昵称"
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
      <el-form-item label="状态" prop="bankName">
        <el-select v-model="queryParams.status" placeholder="请选择">
          <el-option value="" label="全部"></el-option>
          <el-option value="1" label="收入"></el-option>
          <el-option value="2" label="支出"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="银行卡号" prop="bankNo">
        <el-input
          v-model="queryParams.bankNo"
          placeholder="请输入银行卡号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户等级" prop="levelId">
        <el-input
          v-model="queryParams.levelId"
          placeholder="请输入用户等级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="邀请码" prop="inviteCode">
        <el-input
          v-model="queryParams.inviteCode"
          placeholder="请输入邀请码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上级代理" prop="userAgent">
        <el-input
          v-model="queryParams.userAgent"
          placeholder="请输入上级代理"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="昵称" align="center" prop="nickName" />
      <el-table-column label="用户余额" align="center" prop="balance" />
      <el-table-column label="冻结金额" align="center" prop="freezeBalance" />
      <!-- <el-table-column label="登录密码" align="center" prop="loginPwd" /> -->
      <!-- <el-table-column label="支付密码" align="center" prop="payPwd" /> -->
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            @change="changeStatus(scope.row.id,scope.row.status)"
            :active-value="0"
            :inactive-value="1"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="真实姓名" align="center" prop="realName" />
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="银行信息" align="center" prop="bankName" width="200">
        <template slot-scope="scope">
          <div>{{scope.row.bankName}}</div>
          <div>{{scope.row.bankNo}}</div>
          <div>{{scope.row.bankAddr}}</div>
        </template>
      </el-table-column>
      <el-table-column label="信用分" align="center" prop="creditValue" />
      <el-table-column label="用户等级" align="center" prop="levelId" />
      <el-table-column label="头像ID" align="center" prop="avatarId" />
      <el-table-column label="邀请码" align="center" prop="inviteCode" />
      <el-table-column label="上级代理" align="center" prop="userAgent" />
      <el-table-column label="代理节点数" align="center" prop="userAgentNode" />
      <el-table-column label="层级数" align="center" prop="userAgentLevel" />
      <el-table-column label="注册时间" align="center" prop="registerTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.registerTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="注册IP" align="center" prop="registerIp" />
      <el-table-column label="最后登录时间" align="center" prop="lastTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后登录IP" align="center" prop="lastIp" />
      <el-table-column label="备注" align="center" prop="remake" />
      <el-table-column label="是否累加订单(0:是 1:否)" align="center" prop="updateOrder" />
      <el-table-column label="总充值" align="center" prop="deposit" />
      <el-table-column label="总提现" align="center" prop="withdraw" />
      <el-table-column label="总收益" align="center" prop="income" />
      <el-table-column label="总投注" align="center" prop="bet" />
      <el-table-column label="推荐人数" align="center" prop="inviteCount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:user:remove']"
          >删除</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="用户余额" prop="balance">
          <el-input v-model="form.balance" placeholder="请输入用户余额" />
        </el-form-item>
        <el-form-item label="冻结金额" prop="freezeBalance">
          <el-input v-model="form.freezeBalance" placeholder="请输入冻结金额" />
        </el-form-item>
        <el-form-item label="登录密码" prop="loginPwd">
          <el-input v-model="form.loginPwd" placeholder="请输入登录密码" />
        </el-form-item>
        <el-form-item label="支付密码" prop="payPwd">
          <el-input v-model="form.payPwd" placeholder="请输入支付密码" />
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
          <el-input v-model="form.levelId" placeholder="请输入用户等级" />
        </el-form-item>
        <el-form-item label="头像ID" prop="avatarId">
          <el-input v-model="form.avatarId" placeholder="请输入头像ID" />
        </el-form-item>
        <el-form-item label="邀请码" prop="inviteCode">
          <el-input v-model="form.inviteCode" placeholder="请输入邀请码" />
        </el-form-item>
        <el-form-item label="上级代理" prop="userAgent">
          <el-input v-model="form.userAgent" placeholder="请输入上级代理" />
        </el-form-item>
        <el-form-item label="代理节点数" prop="userAgentNode">
          <el-input v-model="form.userAgentNode" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="层级数" prop="userAgentLevel">
          <el-input v-model="form.userAgentLevel" placeholder="请输入层级数" />
        </el-form-item>
        <el-form-item label="注册时间" prop="registerTime">
          <el-date-picker clearable
            v-model="form.registerTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择注册时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="注册IP" prop="registerIp">
          <el-input v-model="form.registerIp" placeholder="请输入注册IP" />
        </el-form-item>
        <el-form-item label="最后登录时间" prop="lastTime">
          <el-date-picker clearable
            v-model="form.lastTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最后登录时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最后登录IP" prop="lastIp">
          <el-input v-model="form.lastIp" placeholder="请输入最后登录IP" />
        </el-form-item>
        <el-form-item label="备注" prop="remake">
          <el-input v-model="form.remake" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="是否累加订单(0:是 1:否)" prop="updateOrder">
          <el-input v-model="form.updateOrder" placeholder="请输入是否累加订单(0:是 1:否)" />
        </el-form-item>
        <el-form-item label="总充值" prop="deposit">
          <el-input v-model="form.deposit" placeholder="请输入总充值" />
        </el-form-item>
        <el-form-item label="总提现" prop="withdraw">
          <el-input v-model="form.withdraw" placeholder="请输入总提现" />
        </el-form-item>
        <el-form-item label="总收益" prop="income">
          <el-input v-model="form.income" placeholder="请输入总收益" />
        </el-form-item>
        <el-form-item label="总投注" prop="bet">
          <el-input v-model="form.bet" placeholder="请输入总投注" />
        </el-form-item>
        <el-form-item label="推荐人数" prop="inviteCount">
          <el-input v-model="form.inviteCount" placeholder="请输入推荐人数" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser } from "@/api/business/user";
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
        nickName: null,
        status: null,
        realName: null,
        phone: null,
        bankNo: null,
        levelId: null,
        inviteCode: null,
        userAgent: null,
        userAgentNode: null,
        registerTime: null,
        status: '',
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
    };
  },
  created() {
    this.getDefaultTime()
    this.getList();
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
        balance: null,
        freezeBalance: null,
        loginPwd: null,
        payPwd: null,
        status: null,
        realName: null,
        phone: null,
        bankName: null,
        bankNo: null,
        bankAddr: null,
        creditValue: null,
        levelId: null,
        avatarId: null,
        inviteCode: null,
        userAgent: null,
        userAgentNode: null,
        userAgentLevel: null,
        registerTime: null,
        registerIp: null,
        lastTime: null,
        lastIp: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        remake: null,
        updateOrder: null,
        deposit: null,
        withdraw: null,
        income: null,
        bet: null,
        inviteCount: null
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
      this.daterangeRegisterTime = [];
      this.resetForm("queryForm");
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
        this.open = true;
        this.title = "修改会员列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUser(this.form).then(response => {
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
    changeStatus(id,status){
      updateUser(
        {
          id: id,
          status : status
        }
      ).then(response => {
        this.$modal.msgSuccess("修改成功");
      });
    },
    getDefaultTime() {
      let end = new Date();
      let start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      this.dateRange[0] = dateFormat("YYYY-mm-dd" , start) + ' 00:00:00'
      this.dateRange[1] = dateFormat("YYYY-mm-dd" , end) + ' 23:59:59'
    }
  }
};
</script>
