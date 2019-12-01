<template>
    <el-container class="job_list">
        <el-main class="main">
            <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
                <el-tab-pane label="全部职位" name="all">
                    <blog_table state="-1" :showEdit="false" :showDelete="false" :activeName="activeName"></blog_table>
                </el-tab-pane>
                <el-tab-pane label="已发布" name="post">
                    <blog_table state="1" :showEdit="true" :showDelete="true" :activeName="activeName"></blog_table>
                </el-tab-pane>
                <el-tab-pane label="草稿箱" name="draft">
                    <blog_table state="0" :showEdit="true" :showDelete="true" :activeName="activeName"></blog_table>
                </el-tab-pane>
            </el-tabs>
        </el-main>
    </el-container>
</template>
<script>
    import {postRequest} from '../utils/api'
    import {putRequest} from '../utils/api'
    import {deleteRequest} from '../utils/api'
    import {getRequest} from '../utils/api'
    import JobBlogTable from './JobBlogTable'
    export default {
        mounted() {
        },
        data() {
            return {
                activeName: 'post',
                isAdmin: false
            };
        },
        methods: {
            handleClick(tab, event) {
            }
        },
        components: {
            'blog_table': JobBlogTable,
        }
    };
</script>
<style>
    .job_list > .header {
        background-color: #ececec;
        margin-top: 10px;
        padding-left: 5px;
        display: flex;
        justify-content: flex-start;
    }

    .job_list > .main {
        /*justify-content: flex-start;*/
        display: flex;
        flex-direction: column;
        padding-left: 0px;
        background-color: #fff;
        padding-top: 0px;
        margin-top: 8px;
    }
</style>
