<template>
  <div class="app-container home">
    <div class="title">商城统计</div>

    <div class="list-item">
      <div class="item-bg1 border-radius5">
        <div class="line-2em">商品总量</div>
        <div class="num">{{dataList.totalGoods}}</div>
        <div>今日商品 <label class="font-blod">{{dataList.todayGoods}}</label></div>
        <div>昨日商品 {{dataList.yesterGoods}}</div>
        <i class="el-icon-files class-one"></i>
      </div>
    </div>
    <div class="list-item">
      <div class="item-bg2 border-radius5">
        <div class="line-2em">用户总量</div>
        <div class="num">{{dataList.totalUsers}}</div>
        <div>今日用户 <label class="font-blod">{{dataList.todayUsers}}</label></div>
        <div>昨日用户 {{dataList.yesterdayUsers}}</div>
        <i class="el-icon-s-custom class-one"></i>
      </div>
    </div>
    <div class="list-item">
      <div class="item-bg3 border-radius5">
        <div class="line-2em">订单总量</div>
        <div class="num">{{dataList.totalOrders}}</div>
        <div>今日订单 <label class="font-blod">{{dataList.todayOrders}}</label></div>
        <div>昨日订单 {{dataList.yesterdayOrders}}</div>
        <i class="el-icon-reading class-one"></i>
      </div>
    </div>
    <div class="list-item">
      <div class="item-bg4 border-radius5">
        <div class="line-2em">订单总金额</div>
        <div class="num">{{dataList.totalOrderAmounts}}</div>
        <div>今日订单总金额 <label class="font-blod">{{dataList.todayOrderAmounts}}</label></div>
        <div>昨日订单总金额 {{dataList.yesterdayOrderAmounts}}</div>
        <i class="el-icon-tickets class-one"></i>
      </div>
    </div>
    <div class="list-item">
      <div class="item-bg5 border-radius5">
        <div class="line-2em">用户充值</div>
        <div class="num">{{dataList.totalDepositAmounts}}</div>
        <div>今日充值 <label class="font-blod">{{dataList.todayDepositAmounts}}</label></div>
        <div>昨日充值 {{dataList.yesterdayDepositAmounts}}</div>
        <i class="el-icon-edit-outline class-one"></i>
      </div>
    </div>
    <div class="list-item">
      <div class="item-bg6 border-radius5">
        <div class="line-2em">商品提现</div>
        <div class="num">{{dataList.totalWithdrawAmounts}}</div>
        <div>今日提现 <label class="font-blod">{{dataList.todayWithdrawAmounts}}</label></div>
        <div>昨日提现 {{dataList.yesterdayWithdrawAmounts}}</div>
        <i class="el-icon-coin class-one"></i>
      </div>
    </div>
    <div class="list-item">
      <div class="item-bg7 border-radius5">
        <div class="line-2em">抢单佣金</div>
        <div class="num">{{dataList.totalCommissionAmounts}}</div>
        <div>今日佣金 <label class="font-blod">{{dataList.todayCommissionAmounts}}</label></div>
        <div>昨日佣金 {{dataList.yesterdayCommissionAmounts}}</div>
        <i class="el-icon-s-marketing class-one"></i>
      </div>
    </div>
    <div class="list-item">
      <div class="item-bg8 border-radius5">
        <div class="line-2em">用户总余额</div>
        <div class="num">{{dataList.totalUserBalance}}</div>
        <div>今日利息宝转出 <label class="font-blod">0</label></div>
        <div>今日利息宝收益 0</div>
        <i class="el-icon-coin class-one"></i>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllReport } from "@/api/index";
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.5",
      dataList: {
        todayCommissionAmounts : 0,//今天的佣金金额
        todayDepositAmounts : 0,//今天存款金额
        todayOrderAmounts : 0,//今天的订单量
        todayOrders : 0,//今日订单
        todayUsers : 0,//今天的用户
        todayWithdrawAmounts : 0,//今天取款金额
        totalCommissionAmounts : 0,//总佣金金额
        totalDepositAmounts : 0,//总存款金额
        totalGoods : 0,//总商品
        totalOrderAmounts : 0,//总订单金额
        totalOrders : 0,//总订单
        totalUserBalance : 0,//总用户余额
        totalUsers : 0,//总用户数
        totalWithdrawAmounts : 0,//总取款金额
        yesterdayCommissionAmounts : 0,//昨日佣金金额
        yesterdayDepositAmounts : 0,//昨天存款金额
        yesterdayOrderAmounts : 0,//昨天订单金额
        yesterdayOrders : 0,//昨天的订单
        yesterdayUsers : 0,//昨天的用户
        yesterdayWithdrawAmounts : 0,//昨天取款金额
        todayGoods: 0,//今日商品
        yesterGoods: 0,//昨日新增商品
      },
    };
  },
  created() {
    this.getData();
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    getData(){
      getAllReport().then(res => {
        this.dataList = res.data
      });
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  .title{
    color: #666;
    margin-bottom: 15px;
  }
  .list-item{
    padding: 7.5px;
    width: 25%;
    position: relative;
    color: #fff;
    float: left;
    border-radius: 5px;
    .border-radius5{
      border-radius: 5px;
    }
    .font-blod{
      font-size: 20px;
      font-weight: bold;
      line-height: 100%;
    }
    .line-2em{
      line-height: 2em;
    }
    .item-bg1{
      background:linear-gradient(-125deg,#57bdbf,#2f9de2);
      padding: 15px 25px;
    }
    
    .item-bg2{
      background:linear-gradient(-125deg,#ff7d7d,#fb2c95);
      padding: 15px 25px;
    }
    .item-bg3{
      background:linear-gradient(-113deg,#c543d8,#925cc3);
      padding: 15px 25px;
    }
    .item-bg4{
      background: linear-gradient(-113deg,#8e8cb3,#2219d0);
      padding: 15px 25px;
    }
    .item-bg5{
      background:linear-gradient(-141deg,#ecca1b,#f39526);
      padding: 15px 25px;
    }
    .item-bg6{
      background: linear-gradient(-141deg,#ec4b1b,#f39526);
      padding: 15px 25px;
    }
    .item-bg7{
      background: linear-gradient(-141deg,#963064,#8a0920);
      padding: 15px 25px;
    }
    .item-bg8{
      background: linear-gradient(-141deg,#2f3331,#565115);
      padding: 15px 25px;
    }
    .num{
      font-size: 26px;
      line-height: 36px;
      font-weight: bold;
    }
    .class-one{
      top: 35%;
      right: 8%;
      font-size: 65px;
      position: absolute;
      color: rgba(255, 255, 255, 0.4);
    }
  }
}
</style>

