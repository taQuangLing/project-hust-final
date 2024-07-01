<template>
    <div class="manager-filter">
        <span class="title">Quản lý</span>
        <el-dropdown @command="handleCommandManager">
            <span class="el-dropdown-link">
                {{ managerSelected.name }}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="item" v-for="item in userList" :key="item.id">{{
            item.name }}</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    props: ['managerSelectedId'],
    data() {
        return {
            userList: [
            ],
        }
    },
    computed: {
        managerSelected() {
            console.log(this.managerSelectedId);
            if (this.managerSelectedId == null || this.managerSelectedId == 0) {
                return {
                    id: 0,
                    name: '<none>'
                }
            } else {
                return this.userList.find(item => item.id == this.managerSelectedId);

            }
        }
    },
    methods: {
        handleCommandManager(command) {
            this.$emit('managerSelected', command);
        },
        async getUserList() {
            await axios.get(this.$store.state.baseUrl + "/admin/v1/employees/manager?userId=" + localStorage.getItem('id'),
                { headers: { Authorization: 'Bearer ' + localStorage.getItem('user') } }
            )
                .then(res => {
                    this.userList = res.data.data;
                })
                .catch(err => {
                    console.log(err)
                    this.$message.error('Lỗi hệ thống')
                })
        },
        
    },
    async mounted() {
        await this.getUserList();
    }
};

</script>

<style>
.manager-filter {
    display: flex;
    align-items: center;
}

.manager-filter .el-dropdown {
    width: 300px;
    border: 1px solid #DCDFE6;
    border-radius: 7px;
    height: 30px;
    background: white;
}

.manager-filter .title {
    color: rgb(41, 41, 41);
    font-size: 14px;
    margin-right: 5px;
}

.manager-filter .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
    margin: 0 10px 0 10px;
    color: rgb(41, 41, 41);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>