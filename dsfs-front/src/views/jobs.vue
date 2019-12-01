<template>
    <div class="jobs">
		<div class="jobBg"></div>
		<div class="title">招聘说明：<span>请投递简历至公司招聘邮箱！</span></div>
		<el-table
		   :data="tableData"
		   header-align="center"
		   empty-text="暂无招聘信息"
		   style="width: 64%;margin: 0 auto;">
		   <el-table-column type="index" label="序号" width="60"></el-table-column>
		   <el-table-column prop="jobName" label="岗位名称" width="180"></el-table-column>
		   <el-table-column prop="needAmount" label="需求人数" width="180"></el-table-column>
		   <el-table-column prop="createdDate" label="发布日期"></el-table-column>
		   <el-table-column label="职位详情">
			   <template slot-scope="scope">
					<span class="btn" @click="lookJD(scope.row)">查看详情</span>
			   </template>
		   </el-table-column>
		</el-table>
		<div class="pagenation">
			<el-pagination
				@size-change="handleSizeChange"
				@current-change="handleCurrentChange"
				:current-page="currentPage"
				:page-sizes="[10, 20, 50]"
				:page-size="pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="total">
			</el-pagination>
		</div>
		<el-dialog
			title="职位详情:"
			:visible.sync="isVisibleJD"
			width="50%">
			<div class="content">
				<div class="jobtitle">工作职责：</div>
				<div class="job" v-html="jobDuty"></div>
				<div class="jobtitle">职位要求：</div>
				<div class="job" v-html="jobRequirements"></div>
			 </div>
			<span slot="footer" class="dialog-footer">
				<span class="btn" type="primary" @click="isVisibleJD = false">我知道了</span>
			</span>
		</el-dialog>
    </div>
</template>
<script>

import {getRequest} from '../utils/api'
export default {
  	name: 'jobs',
	components: {},
	data(){
		return {
			tableData: [{}],
			isVisibleJD: false,
			currentPage: 1,
			pageSize: 10,
			total: 0,
			jobDuty: '1.认真执行国家安全生产的方针政策，法律法规及公司安全生产各项规章制度；\n2.做好公司的安全教育、安全培训工作，参加对项目部的安全生产检查；\n',
			jobRequirements: '1.工程管理、土木工程、工民建等相关专业，大学本科及以上学历；\n2.相关专业5年及以上工作经验，具有较强的沟通协调能力；\n'
		}
	},
	created() {
		this.getJobList();
	},
	methods: {
  	    getJobList(){
            let _this = this;
            getRequest("/job/getJobList?state=1&pageNum="+_this.currentPage+"&pageSize="+_this.pageSize).then(resp=> {
                _this.loading = false;
                if (resp.status == 200) {
                    let res = resp.data;
                    _this.tableData = res.list;
                    _this.total = res.total;
                } else {
                    _this.$message({type: 'error', message: '数据加载失败!'});
                }
            }, resp=> {
                _this.loading = false;
                if (resp.response.status == 403) {
                    var data = resp.response.data;
                    _this.$message({type: 'error', message: data});
                }
            });
		},
		lookJD(row){
			console.log(row)
			this.isVisibleJD = true;
			this.jobDuty = row.jobDuty;
			this.jobRequirements = row.jobRequirements;
		},
		handleSizeChange(val) {
  	        this.pageSize = val;
  	        this.getJobList();
			console.log(`每页 ${val} 条`);
		},
		handleCurrentChange(val) {
  	        this.currentPage = val;
  	        this.getJobList();
			console.log(`当前页: ${val}`);
		}
	}
}
</script>
<style lang="less">
	.jobs {
		text-align: center;
		padding-bottom: 30px;
		.title {
			padding: 20px 0;
			padding-left: 18%;
			font-size: 20px;
			color: #e70012;
			text-align: left;
			font-weight: bold;
		}
		.el-table {
			table {
				border-collapse: collapse;
				th,td {
					border: 1px solid #d8d8d8;
					text-align: center;
				}
			}
		}
		.pagenation {
			padding: 20px 18%;
			text-align: right;
		}
		.jobBg {
			height: 380px;
			background: url(../assets/zp.jpg) no-repeat center center;
			margin-top: 4px;
		}
		.btn {
			cursor: pointer;
			color: deepskyblue;
		}
		.jobtitle {
			padding: 10px 0;
			font-size: 16px;
			font-weight: bold;
		}
		.job{
			white-space: pre-wrap;
			line-height: 20px;
			text-align: left;
		}
	}
</style>
