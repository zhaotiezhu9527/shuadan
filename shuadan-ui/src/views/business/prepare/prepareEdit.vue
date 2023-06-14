<template>
  <!-- 预派送列表 -->
  <div class="app-container">
    <div>
      <div class="prepare-title">
        <router-link to="/trade/prepare" class="prepare-title-btn">
          预派送列表
        </router-link>
      </div>
      <el-card class="box-card margin-top10">
        <el-form :model="queryParams" ref="queryForm" size="small">
            <el-form-item label="用户名" prop="userName">
                <el-input
                class="margin-top10"
                v-model="queryParams.userName"
                placeholder="请输入用户名"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item :label="'触发数量[今日已抢' + 7 + '单]'" prop="userName">
                <el-input
                class="margin-top10"
                v-model="queryParams.userName"
                placeholder="请输入用户名"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="提示文本" prop="userName">
                <el-input
                class="margin-top10"
                v-model="queryParams.userName"
                placeholder="请输入用户名"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="佣金倍数" prop="userName">
                <el-input
                class="margin-top10"
                v-model="queryParams.userName"
                placeholder="请输入佣金倍数"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="queryOpen = true">筛选商品</el-button>
            </el-form-item>
            <el-form-item prop="userName">
                <el-table :data="addList">
                    <el-table-column label="商品id" prop="id" />
                    <el-table-column label="商品名称" prop="goodsName" width="400"/>
                    <el-table-column label="商品价格" prop="goodsPrice" />
                    <el-table-column label="数量">
                        <template slot-scope="scope">
                            <el-input
                            v-model="scope.row.addNo"
                            clearable
                            />
                        </template>
                    </el-table-column>
                    <el-table-column label="订单金额">
                        <template slot-scope="scope">
                            <div>{{ Number(Number(scope.row.goodsPrice) * Number(scope.row.addNo)).toFixed(2) }}</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                        <el-button
                        type="primary"
                        @click="delGoods(scope.row.id)"
                        >删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form-item>
            
            <div class="pre-delivery-foot">
                <el-button type="primary">确 定</el-button>
                <el-button>取 消</el-button>
            </div>
        </el-form>
      </el-card>
    </div>
    <el-dialog title="商品详情" :visible.sync="queryOpen" width="1000px" append-to-body>
      <el-table :data="goodsList">
        <el-table-column label="商品id" prop="id" />
        <el-table-column label="商品名称" prop="goodsName" width="400"/>
        <el-table-column label="商品价格" prop="goodsPrice" />
        <el-table-column label="商品图片" width="120">
            <template slot-scope="scope">
                <img class="list-img-class" :src="resourceDomain.resourceDomain + scope.row.goodsImg" />
            </template>
        </el-table-column>
        <el-table-column label="数量" prop="goodsCount">
            <template slot-scope="scope">
                <el-input
                v-model="scope.row.addNo"
                clearable
                />
            </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
            <el-button
            type="primary"
            @click="addGoods(scope.row)"
            >添加</el-button>
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
import { getPrepare, addPrepare, updatePrepare } from "@/api/business/prepare";
import { listGoods } from "@/api/business/goods";
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
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                userName: null,
            },
            goodsList:[],//商品列表
            addList:[],//添加数据
        };
    },
    created() {
        this.getList();
        this.getCookie();
    },
    methods: {
        /** 查询商品列表列表 */
        getList() {
            this.loading = true;
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
                        goodsImg: item.goodsImg,
                        addNo: 1,
                    }
                    this.goodsList.push(obj)
                });
                this.loading = false;
            });
        },
        // 取消按钮
        cancel() {
            this.open = false;
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
        // 添加
        addGoods(data){
            if (this.addList.length <= 0) {
                this.addList.push(data)
                return;
            }
            let flag = false;
            this.addList.forEach((item,index) => {
                if(item.id === data.id){
                    this.addList[index] = data;
                    flag = true;
                }
            })
            if (!flag) {
                this.addList.push(data)
            }
            this.queryOpen = false
        },
        // 删除
        delGoods(id){
            this.addList.forEach((item,index) => {
                if(item.id === id){
                    this.addList.splice(index,1)
                }
            })
        }
    },
    components: { router }
};
</script>
