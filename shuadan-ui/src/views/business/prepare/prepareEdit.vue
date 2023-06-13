<template>
  <!-- 预派送列表 -->
  <div class="app-container">
    <div>
      <div class="prepare-title">
        <router-link to="/trade/prepare" class="prepare-title-btn">
          预派送列表
        </router-link>
      </div>
    </div>
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
import router from "../../../router";

export default {
    name: "prepareEdit",
    data() {
        return {
            // 遮罩层
            loading: true,
            // 选中数组
            ids: [],
            // 预派送列表表格数据
            prepareList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                userName: [
                    { required: true, message: "用户名不能为空", trigger: "blur" }
                ],
            },
            queryOpen: false,
            queryList: [],
            resourceDomain: {},
        };
    },
    created() {
        this.getList();
        this.getCookie();
        console.log(this.$route.params.tableId);
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
                    }
                    else {
                        addPrepare(this.form).then(response => {
                            this.$modal.msgSuccess("新增成功");
                            this.open = false;
                            this.getList();
                        });
                    }
                }
            });
        },
        // 查看弹窗
        handleQuery(id) {
            this.queryOpen = true;
            getPrepare(id).then(response => {
                this.queryList = response.data.details;
                // this.queryList = data
            });
        },
        getCookie() {
            this.resourceDomain = JSON.parse(Cookies.get("config"));
        },
        // 预派送列表
        gotoList() {
        }
    },
    components: { router }
};
</script>
