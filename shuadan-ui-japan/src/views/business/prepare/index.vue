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
          @click="handleAddGoods"
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
      <el-table-column label="id" align="center" prop="id" width="65"/>
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="今日完成单数" align="center" prop="finishCount" width="108" />
      <el-table-column label="触发单数" align="center" prop="triggerNum" />
      <el-table-column label="批次编号" align="center" prop="preBatch" />
      <el-table-column label="批次金额" align="center" prop="batchOrderAmount" />
      <el-table-column label="商品id" align="center" prop="goodsId" />
      <el-table-column label="商品名称" align="center" width="250">
        <template slot-scope="scope">
          <div v-if="scope.row.goods">{{scope.row.goods.goodsName}}</div>
          <div v-else> - </div>
        </template>
      </el-table-column>
      <el-table-column label="商品数量" align="center" prop="goodsCount" />
      <el-table-column label="佣金倍数" align="center" prop="commissionMul" />
      <el-table-column label="订单金额" align="center" prop="orderAmount" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <div v-if="scope.row.status === 0">未完成</div>
          <div v-else-if="scope.row.status === 1">已完成</div>
        </template>
      </el-table-column>
      <el-table-column label="订单时间" align="center" prop="createTime" width="160"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
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
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="userName">
          <el-input :disabled="true" v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="触发单数" prop="triggerNum">
          <el-input :disabled="true" v-model="form.triggerNum" placeholder="请输入触发单数" />
        </el-form-item>
        <el-form-item label="商品id" prop="goodsId">
          <!-- <el-input v-model="form.goodsId" placeholder="请输入商品id" /> -->
          <el-select v-model="form.goodsId" placeholder="请输入商品id" style="width:880px;" filterable>
            <el-option
              v-for="item in goodsList"
              :key="item.id"
              :label="'【' + item.id  + '】¥' + item.goodsPrice + ' - ' + item.goodsName"
              :value="item.id">
              </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品数量" prop="goodsCount">
          <el-input v-model="form.goodsCount" placeholder="请输入商品数量" />
        </el-form-item>
        <el-form-item label="佣金倍数" prop="commissionMul">
          <el-input v-model="form.commissionMul" placeholder="请输入佣金倍数" />
        </el-form-item>
        <!-- <el-form-item label="提示文本" prop="promptText">
          <el-input v-model="form.promptText" placeholder="请输入提示文本" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPrepare, getPrepare, delPrepare, addPrepare, updatePrepare } from "@/api/business/prepare";
import { listGoods } from "@/api/business/goods";

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
      goodsList:[],//商品列表
    };
  },
  created() {
    this.queryParams.userName = this.$route.query.userName
    if(this.queryParams.userName){
      this.getList()
    }else{
      this.getList();
    }
    this.getGoods()
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
        goodsId: null,
        goodsCount: null,
        preBatch: null,
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
      let data = {}
      data = {
        id: this.form.id,
        goodsId: this.form.goodsId,
        goodsCount: this.form.goodsCount,
        commissionMul: this.form.commissionMul,
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePrepare(data).then(response => {
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
    handleAddGoods() {
      const obj = { path: '/trade/prepare-edit/index' , name: 'prepareEdit' };
      this.$tab.closePage(obj).then(() => {
        this.$router.push({path:'/trade/prepare-edit/index'})
      })
    },
    /** 查询商品列表列表 */
    getGoods() {
        this.goodsList = []
        let obj = {}
        listGoods({
            pageNum: 1,
            pageSize: 10000,
        }).then(response => {
          response.rows.forEach(item => {
              obj = {
                  id: item.id,
                  goodsName: item.goodsName,
                  goodsPrice: item.goodsPrice,
              }
              this.goodsList.push(obj)
          });
        });
    },
  }
};
</script>
