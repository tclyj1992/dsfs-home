<template>
  <div style="text-align: left;margin-top:20px;">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="公司简介" prop="configContent">
        <el-input type="textarea"
                  :rows="5"
                  v-model="ruleForm.configContent"></el-input>
      </el-form-item>
      <el-form-item label="公司图片" prop="region">
          <el-upload
                  action="/dsfs-server/news/uploadimg"
                  list-type="picture-card"
                  :auto-upload="true"
                  :on-success="handleSuccess"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove"
                  :limit="1"
                  :file-list="fileList">
                <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">保存更新</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import {postRequest} from '../utils/api'
  import {putRequest} from '../utils/api'
  import {deleteRequest} from '../utils/api'
  import {getRequest} from '../utils/api'
  export default {
    mounted() {
        this.loadCompany();
    },
    data() {
      return {
          ruleForm: {
              id: null,
              configName: '',
              configCode: '',
              imgUrl: '',
              configContent: '',
          },
          fileList:[],
          dialogImageUrl: '',
          dialogVisible: false,
          disabled: false,
          rules: {
              configContent: [
                  { required: true, message: '请输入活动名称', trigger: 'blur' },
                  { min: 3, max: 1000, message: '长度在 3 到 1000 个字符', trigger: 'blur' }
              ]
          },
          loading:null,
      };
    },
    methods: {
        loadCompany(){
            getRequest("/config/getConfigByConfigCode?configCode=companyDesc").then(resp=> {
                this.loading = false;
                if (resp.status == 200) {
                    let res = resp.data[0];
                    this.ruleForm =res;
                    if(res.imgUrl){
                        this.fileList =[{url: res.imgUrl}];
                    }
                } else {
                    this.$message({type: 'error', message: '页面加载失败!'})
                }
            }, resp=> {
                this.loading = false;
                this.$message({type: 'error', message: '页面加载失败!'})
            })
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    postRequest("/config/saveConfig", {
                        id: this.ruleForm.id,
                        imgUrl: this.ruleForm.imgUrl,
                        configContent: this.ruleForm.configContent
                    }).then(resp=> {
                        this.loading = false;
                        if (resp.status == 200 && resp.data.status == 'success') {
                            this.$message({type: 'success', message:'保存成功!'});
                        }
                    }, resp=> {
                        this.loading = false;
                        this.$message({type: 'error', message: '保存失败!'});
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        handleSuccess(response, file, fileList){
            this.ruleForm.imgUrl = response.data;
            console.log(response)
            console.log(file)
            console.log(fileList)
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        handleRemove(file) {
            console.log(file);
        },
        handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
        },
        handleDownload(file) {
            console.log(file);
        }
    },
    components: {

    }
  };
</script>
<style>

</style>
