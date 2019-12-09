<template>
  <div style="text-align: left;margin-top:20px;">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

        <el-form-item label="公司名称" prop="companyName">
            <el-input v-model="ruleForm.companyName"></el-input>
        </el-form-item>
        <el-form-item label="邮政编码" prop="zipcode">
            <el-input v-model="ruleForm.zipcode"></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
            <el-input v-model="ruleForm.email"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phoneNumber">
            <el-input v-model="ruleForm.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="公司地址" prop="address">
            <el-input type="textarea"
                      :rows="5"
                      v-model="ruleForm.address"></el-input>
        </el-form-item>
      <el-form-item label="地址图片" prop="region">
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
        this.loadAddress();
    },
    data() {
      return {
          ruleForm: {
              id: null,
              companyName: '',
              address: '',
              imgUrl: '',
              zipcode: '',
              phoneNumber: '',
              faxNumber: '',
              email: '',
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
        loadAddress(){
            getRequest("/contact/get").then(resp=> {
                this.loading = false;
                if (resp.status == 200) {
                    let res = resp.data;
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
                    postRequest("/contact/update", this.ruleForm).then(resp=> {
                        this.loading = false;
                        if (resp.status == 200 && resp.data.status == 'success') {
                            this.$message({type: 'success', message:'保存成功!'});
                            this.loadAddress();
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
            let filePath = file.url.substr(file.url.indexOf("/blogimg"));
            let url = "/news/deleteFile?filePath="+filePath;
            deleteRequest(url).then(resp=> {
                if (resp.status == 200) {
                    var data = resp.data;
                    this.$message({type: data.status, message: data.data});
                    if (data.status == 'success') {
                        this.$message({type: data.status, message: data.data});
                    }else {
                        this.$message({type: 'error', message: '删除失败!'});
                    }
                } else {
                    this.$message({type: 'error', message: '删除失败!'});
                }
                this.loading = false;
            }, resp=> {
                this.loading = false;
                this.$message({type: 'error', message: '删除失败!'});
            });
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
