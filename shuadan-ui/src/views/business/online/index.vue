<template>
  <!-- 会员列表 -->
  <div class="app-container">
    <el-table v-loading="loading" :data="userList">
      <el-table-column label="id" align="center" prop="id" width="65"/>
      <el-table-column label="用户名" align="center" prop="userName" width="120">
        <template slot-scope="scope">
          <div class="blue-text" @click="goUser(scope.row.userName)">{{scope.row.userName}}</div>
          <div>{{scope.row.realName}}</div>
        </template>
      </el-table-column>
      <el-table-column label="今日抢单数" align="center" width="100">
        <template slot-scope="scope">
          <div>
            <label class="blue-text" @click="orderList(scope.row)">{{scope.row.orderCount}}</label> 
            / 
            <label class="blue-text">{{scope.row.prepareCount}}</label>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="用户余额" align="center" prop="balance" />
      <el-table-column label="上级用户" align="center" prop="userAgent">
        <template slot-scope="scope">
          <div class="blue-text" @click="goUser(scope.row.userAgent)">{{scope.row.userAgent}}</div>
          <div>{{scope.row.userAgentName}}</div>
        </template>
      </el-table-column>
      <el-table-column label="冻结金额" align="center" prop="freezeBalance" />
      <el-table-column label="最近操作时间" align="center" prop="lastTime" width="180" />
      <el-table-column label="操作ip" align="center" prop="lastIp" width="180" />
      <el-table-column label="操作" width="280" align="center" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button
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
          >提现记录</el-button> -->
          <el-button
            size="small"
            type="primary"
            v-hasPermi="['business:prepare:add']"
            @click="goPrepare(scope.row)"
          >预派送</el-button>
          <el-button
            size="small"
            type="success"
            v-hasPermi="['business:prepare:list']"
            @click="goPrepareList(scope.row)"
          >派送记录</el-button>
          <el-button
            size="small"
            type="danger"
            v-hasPermi="['business:user:logout']"
            @click="offline(scope.row)"
          >强制下线</el-button>
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
  </div>
</template>

<script>
import { listUser,userLogout} from "@/api/business/online";

export default {
  name: "User",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 会员列表表格数据
      userList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        online: 1,
      },
      timer: null,
    };
  },
  created() {
    this.getList();
    this.timer = setInterval(() => {
      this.getList();
    },8000)
  },
  methods: {
    /** 查询会员列表列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      listUser(this.addDateRange(this.queryParams)).then(response => {
        this.userList = response.rows;
        this.total = response.total;
        this.loading = false;
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
    handleUserAgent(username){
      if(username === 'root'){
        return false
      }
      this.queryParams.userName = username
      this.getList()
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
    // 预派送
    goPrepare(data){
      const obj = { path: '/trade/prepare-edit/index' , name: 'prepareEdit' };
      this.$tab.closePage(obj).then(() => {
        this.$router.push({path:'/trade/prepare-edit/index' ,query:{userName: data.userName}})
      })
    },
    // 派送记录
    goPrepareList(data){
      const obj = { path: '/trade/prepare' , name: 'Prepare' };
      this.$tab.closePage(obj).then(() => {
        this.$router.push({path:'/trade/prepare' ,query:{userName: data.userName}})
      })
    },
    // 跳进会员列表
    goUser(userName)  {
      const obj = { path: '/user/user' , name: 'User' };
      this.$tab.closePage(obj).then(() => {
        this.$router.push({path:'/user/user' ,query:{userName: userName}})
      })
    },
    // 跳转订单列表
    orderList(data){
      const obj = { path: '/trade/order' , name: 'Order' };
      this.$tab.closePage(obj).then(() => {
        this.$router.push({path:'/trade/order' ,query:{userName: data.userName}})
      })
    },
    // 强制下线
    offline(row){
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认强制会员"' + row.userName + '"下线？').then(function() {
        return userLogout({userName: row.userName});
      }).then(res => {
        this.getList();
        this.$modal.msgSuccess(res.msg);
      }).catch(() => {});
    },
  },
  beforeDestroy() {
    clearInterval(this.timer);        
    this.timer = null;
  }
};
</script>
