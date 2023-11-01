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
                @blur="getPrepareNewOneList"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item :label="'触发数量[今日已抢' + queryParams.Num + '单]'" prop="userName">
                <el-input
                class="margin-top10"
                v-model="queryParams.triggerNum"
                placeholder="请输入触发单数"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="提示文本" prop="promptText">
                <el-input
                class="margin-top10"
                v-model="queryParams.promptText"
                placeholder="加急单"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="佣金倍数" prop="commissionMul">
                <el-input
                class="margin-top10"
                v-model="queryParams.commissionMul"
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
                <el-button type="primary" @click="addPrepare">确 定</el-button>
                <el-button>取 消</el-button>
            </div>
        </el-form>
      </el-card>
    </div>
    <el-dialog title="商品详情" :visible.sync="queryOpen" width="1000px" append-to-body>
        <el-form :model="listParams" ref="queryForm" size="small" :inline="true" label-width="68px">
            <el-form-item label="商品名称" prop="goodsName">
                <el-input
                v-model="listParams.goodsName"
                placeholder="请输入商品名称"
                clearable
                />
                
            </el-form-item>
            <el-form-item label="最小金额" prop="minPrice">
                <el-input
                v-model="listParams.minPrice"
                placeholder="请输入最小金额"
                clearable
                />
            </el-form-item>
            <el-form-item label="最大金额" prop="maxPrice">
                <el-input
                v-model="listParams.maxPrice"
                placeholder="请输入最大金额"
                clearable
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="getList">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQueryList">重置</el-button>
            </el-form-item>
        </el-form>
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
import { getPrepare, addPrepare, updatePrepare,getPrepareNewOne } from "@/api/business/prepare";
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
                userName: null,
                Num:0,
                goodsSelect:'',
                promptText: '',
            },
            goodsList:[],//商品列表
            addList:[],//添加数据
            listParams: {
                goodsName: "", 
                minPrice: "",
                maxPrice: "",
                pageNum: 1,
                pageSize: 10000,
            },//列表查询参数
        };
    },
    created() {
        this.getList();
        this.getCookie();
        this.queryParams.userName = this.$route.query.userName
        if(this.queryParams.userName){
            this.getPrepareNewOneList()
        }
    },
    methods: {
        /** 查询商品列表列表 */
        getList() {
            this.loading = true;
            this.goodsList = []
            let obj = {}
            listGoods(this.listParams).then(response => {
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
            this.queryParams.promptText = JSON.parse(Cookies.get("urgentText"));
        },
        // 添加
        addGoods(data){
            // this.queryOpen = false
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
        },
        // 删除
        delGoods(id){
            this.addList.forEach((item,index) => {
                if(item.id === id){
                    this.addList.splice(index,1)
                }
            })
        },
        // 提交预派送
        addPrepare(){
            let str = ''
            this.addList.forEach(item => {
                str += item.id + ":" + item.addNo + ','
            })
            this.queryParams.goodsSelect = str.substring(0 ,str.length - 1)
            addPrepare(this.queryParams).then(response => {
                if(response.code === 200){
                    this.$modal.msgSuccess(response.msg);
                    const obj = { path: '/trade/prepare-edit/index' , name: 'prepareEdit' };
                    this.$tab.closePage(obj).then(() => {
                        this.$router.push({path:'/trade/prepare'})
                    })
                }
            });
        },
        resetQueryList(){
            this.listParams =  {
                goodsName: "", 
                minPrice: "",
                maxPrice: "",
                pageNum: 1,
                pageSize: 10000,
            }
        },
        // 查询预派送新增列表
        getPrepareNewOneList(){
            getPrepareNewOne(this.queryParams.userName).then(response => {
                this.queryParams.Num = response.data
            });
        },
    },
    components: { router }
};
</script>
