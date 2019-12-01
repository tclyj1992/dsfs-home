<template>
  <div class="home">
    <div class="banner"></div>
    <div class="container">
		<div class="item">
			<div class="title">公司简介</div>
			<img class="imgC" src="../assets/info.jpg">
			<span style="line-height: 30px;font-size: 16px;font-family: 'Signika Negative', sans-serif;">
				</br>&emsp;&emsp;成都鼎盛防水装饰工程有限公司办公室地址位于有着3000余年的建城史，故有“锦官城”之称的成都，成都 成都市武侯区双楠路5号，于2007年07月18日在武侯区市场和质量监督管理局注册成立，注册资本为500 万元人民币，在公司发展壮大的12年里，我们始终为客户提供好的产品和技术支持、健全的售后服务，我公司主要经营防水装饰工程、保温工程、防腐工程设计、施工；室内外装饰装修；建筑幕墙工程设计施工；园林绿化工程施工。（依法须经批准的项目，经相关部门批准后方可开展经营活动）。，我们有好的产品和专业的销售和技术团队，我公司属于成都装饰公司装饰设计公司黄页行业，如果您对我公司的产品服务有兴趣，期待您在线留言或者来电咨询
			</span> 
			<div style="clear: both;"></div>
		</div>
		<div class="item">
			<div class="title">企业文化</div>
			<div class="lineText" style="padding:12px 0;color: #383838;font-weight: bold;">我们只专注一件事情——工程项目管理</div>
			<div class="lineText" style="padding-left: 30px;"><span>确保工程无重大安全事故</span></div>
			<div class="lineText" style="padding-left: 60px;"><span>确保工程施工单位无大面积返工</span></div>
			<div class="lineText" style="padding-left: 120px;"><span>确保工程材料及设备无伪劣产品</span></div>
			<div class="lineText" style="padding-left: 180px;"><span>确保工程管理留下痕迹、实施过程可追溯</span></div>
		</div>
		<div class="item">
			<div class="title">员工风貌</div>
			<el-carousel :interval="4000" type="card" height="300px">
				<el-carousel-item v-for="(item,i) in teamImgs" :key="i">
					<img :src="item" alt="">
				</el-carousel-item>
			</el-carousel>
		</div>
		<!--<div class="item">
			<div class="title">领导寄语</div>
			<div class="leaderSay">
				<div class="l">
					<div class="top">领导寄语：</div>
					<div class="bottom">员工勤奋，创造一个形象好的公司(噼里啪啦总结性的描述性语言)</div>
				</div>
				<div class="r">
					<div class="tip" v-for="v in imgs">
						<img :src="v" alt="@">
						<div class="tipName">总经理：XXX</div>
						<div class="hoverTip"><span>我们只专注一件事情——工程项目管理</span></div>
					</div>
				</div>
			</div>
		</div>-->
    </div>

  </div>
</template>

<script>
	import a1 from '../assets/n1.png';
	import a2 from '../assets/n2.png';

	import t1 from '../assets/team/111.jpg';
	import t2 from '../assets/team/222.jpg';
	import t3 from '../assets/team/333.jpg';
export default {
  name: 'home',
  components: {},
  data() {
    return {
		imgs: [a1,a2,a1,a2,a1,a2],
		teamImgs:[t1,t2,t3],
    }
  },
  methods: {

  }
}
</script>
<style lang="less">
@media (min-width: 1200px){
	.container {
		width: 1170px;
	}
}
@media (min-width: 992px){
	.container {
		width: 1170px;
	}
}
@media (min-width: 768){
	.container {
		width: 750px;
	}
}
  .home {
    .banner {
      height: 400px;
      padding: 12px 24px;
      line-height: 400px;
      text-align: center;
      font-size: 50px;
      font-weight: bold;
      background: url(../assets/homeBg.jpg) no-repeat 0 0;
      background-size: 100% 100%;
    }
    .container {
      display: flex;
      flex-direction: column;
      min-height: 500px;
	  max-width: 1170px;
      margin: 0 auto;
	  .title {
		  text-align: center;
		  font-size: 24px;
		  color: #00a3e4;
		  padding: 24px;
		  text-shadow: 2px 2px 4px #e8e8e8;
	  }
      .item {
        background-color: #fff;
        height: 100%;
        flex-grow: 1;
        padding-top: 24px;
        .imgC {
          position: relative;
          top: 0;
          float: left;
          width: 570px;
          height: 377px;
          margin-right: 30px;
          margin-right: 12px;
		  box-shadow: 0px 1px 4px rgba(0,0,0,0.3);
        }
		.lineText {
			line-height: 40px;
			font-size: 20px;
		}
		.lineText:hover span{
			border-left: 5px solid #e70012;
			padding-left: 12px;
		}
		.el-carousel__item:nth-child(2n) {
			background-color: #99a9bf;
		}
	  
		.el-carousel__item:nth-child(2n+1) {
			background-color: #d3dce6;
		}

      }
    }

  }
</style>