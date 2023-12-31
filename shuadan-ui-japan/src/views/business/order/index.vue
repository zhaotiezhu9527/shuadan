<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易状态">
        <el-select v-model="queryParams.status" placeholder="请选择">
          <el-option value="" label="全部"></el-option>
          <el-option value="0" label="待支付"></el-option>
          <el-option value="1" label="已完成"></el-option>
          <el-option value="2" label="冻结中"></el-option>
          <el-option value="3" label="已取消"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="订单时间" prop="orderTime">
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
          v-hasPermi="['business:order:add']"
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
          v-hasPermi="['business:order:edit']"
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
          v-hasPermi="['business:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" width="60"/>
      <el-table-column label="订单号" align="center" prop="orderNo" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="昵称" align="center" prop="nickName" />
      <el-table-column label="商品价格" align="center" prop="goodsPrice" />
      <el-table-column label="交易数量" align="center" prop="goodsCount" />
      <el-table-column label="订单金额" align="center" prop="orderAmount" />
      <el-table-column label="佣金" align="center" prop="commission" />
      <el-table-column label="交易状态" align="center" prop="status">
        <template slot-scope="scope">
          <div v-if="scope.row.status === 0">待支付</div>
          <div v-else-if="scope.row.status === 1">已完成</div>
          <div v-else-if="scope.row.status === 2">冻结中</div>
          <div v-else-if="scope.row.status === 3">已取消</div>
          <div v-else> - </div>
        </template>
      </el-table-column>
      <el-table-column label="下单时间" align="center" prop="orderTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="今日第几单" align="center" prop="countNum" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleUpdateBalance(scope.row)"
            v-hasPermi="['business:order:editAmount']"
            v-if="scope.row.status === 0"
          >修改订单金额</el-button>
          <el-button
            size="mini"
            type="text"
            @click="cancelOrder(scope.row)"
            v-hasPermi="['business:order:cancel']"
            v-if="scope.row.status === 0"
          >取消订单</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:order:remove']"
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

    <!-- 添加或修改订单列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="商品ID" prop="goodsId">
          <el-input v-model="form.goodsId" placeholder="请输入商品ID" />
        </el-form-item>
        <el-form-item label="商品价格" prop="goodsPrice">
          <el-input v-model="form.goodsPrice" placeholder="请输入商品价格" />
        </el-form-item>
        <el-form-item label="商品数量" prop="goodsCount">
          <el-input v-model="form.goodsCount" placeholder="请输入商品数量" />
        </el-form-item>
        <el-form-item label="订单金额" prop="orderAmount">
          <el-input v-model="form.orderAmount" placeholder="请输入订单金额" />
        </el-form-item>
        <el-form-item label="佣金" prop="commission">
          <el-input v-model="form.commission" placeholder="请输入佣金" />
        </el-form-item>
        <el-form-item label="佣金比例" prop="commissionRate">
          <el-input v-model="form.commissionRate" placeholder="请输入佣金比例" />
        </el-form-item>
        <el-form-item label="订单时间" prop="orderTime">
          <el-date-picker clearable
            v-model="form.orderTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择订单时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="支付时间" prop="payTime">
          <el-date-picker clearable
            v-model="form.payTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择支付时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预派送ID" prop="preId">
          <el-input v-model="form.preId" placeholder="请输入预派送ID" />
        </el-form-item>
        <el-form-item label="预派送详情ID" prop="preDetailId">
          <el-input v-model="form.preDetailId" placeholder="请输入预派送详情ID" />
        </el-form-item>
        <el-form-item label="今日第几单" prop="countNum">
          <el-input v-model="form.countNum" placeholder="请输入今日第几单" />
        </el-form-item>
        <el-form-item label="佣金倍数" prop="commissionMul">
          <el-input v-model="form.commissionMul" placeholder="请输入佣金倍数" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 修改交易余额对话框 -->
    <el-dialog title="增减余额" :visible.sync="balanceOpen" width="500px" append-to-body>
      <el-form ref="balanceform" :model="balanceForm" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="userName">
          <el-input :disabled="true" v-model="balanceForm.userName" placeholder="请输入4-12位数字或字母" />
        </el-form-item>
        <el-form-item label="订单金额" prop="orderAmount">
          <el-input v-model="balanceForm.orderAmount" placeholder="请输入金额" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="balanceSub">确 定</el-button>
        <el-button @click="balanceOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOrder, getOrder, delOrder, addOrder, updateOrder,editAmount,cancelOrder } from "@/api/business/order";
import { dateFormat } from '@/utils/auth'

export default {
  name: "Order",
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
      // 订单列表表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        userName: null,
        status: "",
        orderTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNo: [
          { required: true, message: "订单号不能为空", trigger: "blur" }
        ],
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
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
      // 是否显示修改余额弹出层
      balanceOpen: false,
      // 修改余额表单数据
      balanceForm: {
        orderAmount: '',
        id:'',
        userName: '',
      },
    };
  },
  created() {
    this.getDefaultTime()
    this.queryParams.userName = this.$route.query.userName
    if(this.queryParams.userName){
      this.getList()
    }else{
      this.getList();
    }
  },
  methods: {
    /** 查询订单列表列表 */
    getList() {
      this.loading = true;
      listOrder(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.orderList = response.rows;
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
        orderNo: null,
        userName: null,
        nickName: null,
        orderAmount: null,
        commission: null,
        commissionRate: null,
        status: null,
        orderTime: null,
        payTime: null,
        preId: null,
        preDetailId: null,
        countNum: null,
        commissionMul: null
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
      this.title = "添加订单列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除订单列表编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    },
    getDefaultTime() {
      let end = new Date();
      let start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      this.dateRange[0] = dateFormat("YYYY-mm-dd" , end) + ' 00:00:00'
      this.dateRange[1] = dateFormat("YYYY-mm-dd" , end) + ' 23:59:59'
    },
    // 修改订单金额
    handleUpdateBalance(data){
      this.balanceOpen = true;
      this.balanceForm.userName = data.userName
      this.balanceForm.id = data.id
      this.balanceForm.orderAmount = data.orderAmount
    },
    // 修改余额提交方法
    balanceSub(){
      this.$refs["balanceform"].validate(valid => {
        if (valid) {
          editAmount(this.balanceForm).then(response => {
            this.$modal.msgSuccess(response.msg);
            this.balanceOpen = false;
            this.getList();
          });
        }
      });
    },
    /** 取消订单按钮操作 */
    cancelOrder(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认取消编号为"' + ids + '"的订单？').then(function() {
        return cancelOrder({id:ids});
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {});
    },
  }
};
</script>
