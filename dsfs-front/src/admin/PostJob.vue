<template>
  <div style="text-align: left;margin-top:20px;">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="岗位名称" prop="jobName">
        <el-input v-model="ruleForm.jobName"></el-input>
      </el-form-item>
      <el-form-item label="需求人数" prop="needAmount">
        <el-input v-model="ruleForm.needAmount"></el-input>
      </el-form-item>
      <el-form-item label="工作职责" prop="jobDutyArr">
        <div style="position: relative" v-for="(item, i) in jobDutyArr" :key="i">
          <el-input v-model="item.line" style="margin-bottom:5px;width:calc(100% - 30px)"></el-input>
          <i style="position: absolute;right: 5px;top: 10px;font-size:20px;color:deepskyblue" class="el-icon-remove"@click="deleteCurrentLine('jobDutyArr',i)"></i>
        </div>
          <el-button @click="createNewLine('jobDutyArr')">新增一行</el-button>
      </el-form-item>
      <el-form-item label="职位要求" prop="jobRequirementsArr">
        <div style="position: relative" v-for="(item, i) in jobRequirementsArr" :key="i">
          <el-input v-model="item.line" style="margin-bottom:5px;width:calc(100% - 30px)"></el-input>
          <i style="position: absolute;right: 5px;top: 10px;font-size:20px;color:deepskyblue" class="el-icon-remove"@click="deleteCurrentLine('jobRequirementsArr',i)"></i>
        </div>
        <el-button @click="createNewLine('jobRequirementsArr')">新增一行</el-button>
      </el-form-item>
    </el-form>
    <div style="display: flex;align-items: center;margin-top: 15px;justify-content: flex-end">
      <el-button @click="cancelEdit" v-if="from!=undefined">放弃修改</el-button>
      <template v-if="from==undefined || from=='draft'">
        <el-button @click="submitForm(0)">保存到草稿箱</el-button>
        <el-button type="primary" @click="submitForm(1)">发布职位</el-button>
      </template>
      <template v-else="from==post">
        <el-button type="primary" @click="submitForm(1)">保存修改</el-button>
      </template>
    </div>
  </div>
</template>
<script>
  import {postRequest} from '../utils/api'
  import {putRequest} from '../utils/api'
  import {deleteRequest} from '../utils/api'
  import {getRequest} from '../utils/api'
  import {uploadFileRequest} from '../utils/api'
  import {isNotNullORBlank} from '../utils/utils'

  export default {
    mounted() {
        console.log("mounted");
        var from = this.$route.query.from;
        this.from = from;
        if (from != null && from != '' && from != undefined) {
            var id = this.$route.query.id;
            this.ruleForm.id = id;
            this.loading = true;
            getRequest("/job/findById?jobId=" + id).then(resp=> {
                this.loading = false;
                if (resp.status == 200) {
                    this.ruleForm = resp.data;
                    this.jobDutyArr = this.ruleForm.jobDuty.split("\n").map(v=>{return {line:v}});
                    this.jobRequirementsArr = this.ruleForm.jobRequirements.split("\n").map(v=>{return {line:v}});
                } else {
                    this.$message({type: 'error', message: '页面加载失败!'});
                }
            }, resp=> {
                this.loading = false;
                this.$message({type: 'error', message: '页面加载失败!'});
            })
        }else {
            this.ruleForm = {
                id: -1,
                jobName: '',
                needAmount: 1,
                jobDuty: '',
                jobRequirements: '',
                state:null
            };
        }
    },
    components: {

    },
    methods: {
        createNewLine(m){
            this[m].push({line:''});
        },
        deleteCurrentLine(m,i){
            this[m].splice(i,1)
        },
        cancelEdit(){
            this.$router.go(-1)
        },
        submitForm(state) {
            this.ruleForm.jobDuty = this.jobDutyArr.map(v => {return v.line}).join('\n')
            this.ruleForm.jobRequirements = this.jobRequirementsArr.map(v => {return v.line}).join('\n')
            console.log(this.ruleForm);
            this.loading = true;
            postRequest("/job/saveJob", {
                id: this.ruleForm.id,
                jobName: this.ruleForm.jobName,
                needAmount: this.ruleForm.needAmount,
                jobDuty: this.ruleForm.jobDuty,
                jobRequirements: this.ruleForm.jobRequirements,
                state:state
            }).then(resp=> {
                this.loading = false;
                if (resp.status == 200 && resp.data.status == 'success') {
                    //this.ruleForm.id = resp.data.data;
                    this.$message({type: 'success', message: state == 0 ? '保存成功!' : '发布成功!'});
                    window.bus.$emit('JobBlogTableReload')
                    if (state == 1) {
                        this.$router.replace({path: '/jobList'});
                    }
                }
            }, resp=> {
                this.loading = false;
                this.$message({type: 'error', message: state == 0 ? '保存草稿失败!' : '职位发布失败!'});
            })
        }
    },
    data() {
      return {
          from: '',
          ruleForm: {
              id: -1,
              jobName: '',
              needAmount: 1,
              jobDuty: '',
              jobRequirements: '',
              state:null
          },
          jobDutyArr: [{line:''}],
          jobRequirementsArr: [{line:''}],
          rules: {
              jobName: [
                  { required: true, message: '请输入职位名称', trigger: 'blur' },
                  { min: 3, max: 5, message: '长度在 3 到 20 个字符', trigger: 'blur' }
              ],
              date1: [
                  { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
              ],
              date2: [
                  { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
              ],
              type: [
                  { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
              ],
              resource: [
                  { required: true, message: '请选择活动资源', trigger: 'change' }
              ],
              desc: [
                  { required: true, message: '请填写活动形式', trigger: 'blur' }
              ]
          }
      }
    }
  }
</script>
<style>
  .post-article > .main > #editor {
    width: 100%;
    height: 450px;
    text-align: left;
  }

  .post-article > .header {
    background-color: #ececec;
    margin-top: 10px;
    padding-left: 5px;
    display: flex;
    justify-content: flex-start;
  }

  .post-article > .main {
    /*justify-content: flex-start;*/
    display: flex;
    flex-direction: column;
    padding-left: 5px;
    background-color: #ececec;
    padding-top: 0px;
  }

  .post-article > .header > .el-tag + .el-tag {
    margin-left: 10px;
  }

  .post-article > .header > .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }

  .post-article > .header > .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }

  .post-article {
  }
</style>
