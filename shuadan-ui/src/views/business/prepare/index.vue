<template>
  <!-- 预派送列表 -->
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
          v-hasPermi="['business:prepare:add']"
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
          v-hasPermi="['business:prepare:edit']"
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
          v-hasPermi="['business:prepare:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:prepare:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="prepareList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" width="60"/>
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="今日完成单数" align="center" prop="finishCount" />
      <el-table-column label="触发单数" align="center" prop="triggerNum" />
      <el-table-column label="提示文本" align="center" prop="promptText" />
      <el-table-column label="佣金倍数" align="center" prop="commissionMul" />
      <el-table-column label="订单总额" align="center" prop="orderAmount" />
      
      <el-table-column label="商品ID：数量" align="center" prop="status">
        <template slot-scope="scope">
          <div v-for="(item,index) in scope.row.details" :key="index">
          {{ item.goodsId }}:{{ item.goodsCount }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="下单时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" type="danger">未完成</el-tag>
          <el-tag v-else-if="scope.row.status === 1" type="success">已完成</el-tag>
          <el-tag v-else> - </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleQuery(scope.row.id)"
            v-hasPermi="['business:prepare:query']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:prepare:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:prepare:remove']"
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

    <!-- 添加或修改预派送列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="userName">
          <el-input :disabled="form.id ? true : false" v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="触发单数" prop="triggerNum">
          <el-input v-model="form.triggerNum" placeholder="请输入触发单数" />
        </el-form-item>
        <el-form-item label="提示文本" prop="promptText">
          <el-input v-model="form.promptText" placeholder="请输入提示文本" />
        </el-form-item>
        <el-form-item label="佣金倍数" prop="commissionMul">
          <el-input v-model="form.commissionMul" placeholder="请输入佣金倍数" />
        </el-form-item>
        <el-form-item label="订单总额" prop="orderAmount">
          <el-input v-model="form.orderAmount" placeholder="请输入订单总额" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看预派送列表对话框 -->
    <el-dialog title="商品详情" :visible.sync="queryOpen" width="800px" append-to-body>
      <el-table :data="queryList">
        <el-table-column label="商品id" prop="goodsId" />
        <el-table-column label="商品名称" prop="goods.goodsName" width="400"/>
        <el-table-column label="商品价格" prop="goods.goodsPrice" />
        <el-table-column label="数量" prop="goodsCount" />
        <el-table-column label="商品图片" width="120">
          <template slot-scope="scope">
          <img class="list-img-class" :src="resourceDomain.resourceDomain + scope.row.goods.goodsImg" />
        </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="queryOpen = false">确 定</el-button>
        <el-button @click="queryOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPrepare, getPrepare, delPrepare, addPrepare, updatePrepare } from "@/api/business/prepare";
import Cookies from "js-cookie";

export default {
  name: "Prepare",
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
      // 预派送列表表格数据
      prepareList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
      },
      queryOpen: false,//查看弹窗状态
      queryList: [],//查看数据
      resourceDomain: {},
    };
  },
  created() {
    this.getList();
    this.getCookie()
  },
  methods: {
    /** 查询预派送列表列表 */
    getList() {
      this.loading = true;
      listPrepare(this.queryParams).then(response => {
        this.prepareList = response.rows;
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
        triggerNum: null,
        promptText: null,
        commissionMul: null,
        orderAmount: null,
        status: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        remark: null
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
      this.title = "添加预派送列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPrepare(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改预派送列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePrepare(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPrepare(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除预派送列表编号为"' + ids + '"的数据项？').then(function() {
        return delPrepare(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/prepare/export', {
        ...this.queryParams
      }, `prepare_${new Date().getTime()}.xlsx`)
    },
    // 查看弹窗
    handleQuery(id){
      this.queryOpen = true
      getPrepare(id).then(response => {
        this.queryList = response.data.details;
        // this.queryList = data
      });
    },
    getCookie() {
      this.resourceDomain = JSON.parse(Cookies.get("config"));
    },
  }
};
</script>
