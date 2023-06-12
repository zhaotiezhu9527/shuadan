<template>
  <!-- 会员等级 -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="等级名称" prop="levelName">
        <el-input
          v-model="queryParams.levelName"
          placeholder="请输入等级名称"
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
          v-hasPermi="['business:level:add']"
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
          v-hasPermi="['business:level:edit']"
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
          v-hasPermi="['business:level:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:level:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="levelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <!-- <el-table-column label="等级值" align="center" prop="levelValue" /> -->
      <el-table-column label="名称" align="center" prop="levelName" />
      <el-table-column label="等级图标" align="center" prop="levelIcon">
        <template slot-scope="scope">
          <img class="level-img-class" :src="resourceDomain.resourceDomain + scope.row.levelIcon" />
        </template>
      </el-table-column>
      <el-table-column label="会员价格" align="center" prop="levelPrice" />
      <el-table-column label="佣金比例" align="center" prop="commissionRate">
        <template slot-scope="scope">
          <span>{{ scope.row.commissionRate }}%</span>
        </template>
      </el-table-column>
      <el-table-column label="订单量" align="center" prop="dayOrderCount" />
      <el-table-column label="提现次数" align="center" prop="dayWithdrawCount" />
      <el-table-column label="最小提现金额" align="center" prop="minWithdrawAmount" />
      <el-table-column label="最大提现金额" align="center" prop="maxWithdrawAmount" />
      <el-table-column label="提现手续费" align="center" prop="withdrawFee" />
      <el-table-column label="提现至少完成订单/天" align="center" prop="withdrawOrderCount" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:level:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:level:remove']"
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

    <!-- 添加或修改等级列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="等级值">
          <el-input :disabled="form.id" v-model="form.levelValue" placeholder="等级值" />
        </el-form-item>
        <el-form-item label="等级名称" prop="levelName">
          <el-input v-model="form.levelName" placeholder="请输入等级名称" />
        </el-form-item>
        <el-form-item label="等级价格" prop="levelPrice">
          <el-input v-model="form.levelPrice" placeholder="请输入等级价格" />
        </el-form-item>
        <el-form-item label="等级图标" prop="levelIcon">
          <el-upload
              class="avatar-uploader"
              :action="upload.url"
              :file-list="upload.fileList"
              :headers="upload.headers"
              :show-file-list="false"
              :on-success="successHandle"
              :before-upload="beforeUploadHandle">
              <img v-if="form.levelIcon" :src="resourceDomain.resourceDomain + form.levelIcon" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
        <el-form-item label="每日订单量" prop="dayOrderCount">
          <el-input v-model="form.dayOrderCount" placeholder="请输入每日订单量" />
        </el-form-item>
        <el-form-item label="佣金比例" prop="commissionRate">
          <el-input v-model="form.commissionRate" placeholder="请输入佣金比例" />
        </el-form-item>
        <el-form-item label="每日提现次数" prop="dayWithdrawCount">
          <el-input v-model="form.dayWithdrawCount" placeholder="请输入每日提现次数" />
        </el-form-item>
        <el-form-item label="最小提现金额" prop="minWithdrawAmount">
          <el-input v-model="form.minWithdrawAmount" placeholder="请输入最小提现金额" />
        </el-form-item>
        <el-form-item label="最大提现金额" prop="maxWithdrawAmount">
          <el-input v-model="form.maxWithdrawAmount" placeholder="请输入最大提现金额" />
        </el-form-item>
        <el-form-item label="提现手续费" prop="withdrawFee">
          <el-input v-model="form.withdrawFee" placeholder="请输入提现手续费" />
        </el-form-item>
        <el-form-item label="提现至少完成订单/天" prop="withdrawOrderCount">
          <el-input v-model="form.withdrawOrderCount" placeholder="请输入提现需要完成 几笔订单才开提现 / 天" />
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
  </div>
</template>

<script>
import { listLevel, getLevel, delLevel, addLevel, updateLevel } from "@/api/business/level";
import { getToken } from "@/utils/auth";
import Cookies from "js-cookie";


export default {
  name: "Level",
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
      // 等级列表表格数据
      levelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        levelValue: null,
        levelName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        levelValue: [
          { required: true, message: "等级值不能为空", trigger: "blur" }
        ],
      },
      resourceDomain: {},
      // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/info/upload",
        // 上传的文件列表
        fileList: []
      },
    };
  },
  created() {
    this.getList();
    this.getCookie()
  },
  methods: {
    /** 查询等级列表列表 */
    getList() {
      this.loading = true;
      listLevel(this.queryParams).then(response => {
        this.levelList = response.rows;
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
        levelValue: null,
        levelName: null,
        levelPrice: null,
        levelIcon: null,
        dayOrderCount: null,
        commissionRate: null,
        dayWithdrawCount: null,
        minWithdrawAmount: null,
        maxWithdrawAmount: null,
        withdrawFee: null,
        withdrawOrderCount: null,
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
      this.title = "添加等级列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLevel(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改等级列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateLevel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLevel(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除等级列表编号为"' + ids + '"的数据项？').then(function() {
        return delLevel(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/level/export', {
        ...this.queryParams
      }, `level_${new Date().getTime()}.xlsx`)
    },
    getCookie() {
      this.resourceDomain = JSON.parse(Cookies.get("config"));
    },
    beforeUploadHandle (file) {
      this.formLoading = true
      if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
        this.$message.error('只支持jpg、png、gif格式的图片！')
        return false
      }
    },
    // 上传成功
    successHandle (response, file, fileList) {
      this.fileList = fileList
      if (response && response.code === 200) {
        this.form.levelIcon = response.data.filePath;
      } else {
        // this.$message.error(response.msg)
      }
      this.formLoading = false
    },
  }
};
</script>
<style>
.level-img-class{
  height: 20px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
