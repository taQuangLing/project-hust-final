<template>
    <div class="position-filter">
        <span class="title">Vị trí</span>
        <el-dropdown @command="handleCommandPosition">
            <span class="el-dropdown-link">
                {{ positionSelected.name }}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="item" v-for="item in positionList" :key="item.id">{{
            item.name }}</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    props: ['positionSelectedId'],
    data() {
        return {
            positionList: [
            ],
        }
    },
    computed: {
        positionSelected() {
            console.log(this.positionSelectedId);
            if (this.positionSelectedId == null || this.positionSelectedId == 0) {
                return {
                    id: 0,
                    name: '<none>'
                }
            } else {
                return this.positionList.find(item => item.id == this.positionSelectedId);

            }
        }
    },
    methods: {
        handleCommandPosition(command) {
            this.$emit('positionSelected', command);
        },
        async getPositionList() {
            await axios.get(this.$store.state.baseUrl + "/admin/v1/positions?userId=" + localStorage.getItem('id'),
                { headers: { Authorization: 'Bearer ' + localStorage.getItem('user') } }
            )
                .then(res => {
                    this.positionList = res.data.data;
                })
                .catch(err => {
                    console.log(err)
                    this.$message.error('Lỗi hệ thống')
                })
        }
    },
    async mounted() {
        await this.getPositionList();
    }
};

</script>

<style>
.position-filter {
    display: flex;
    align-items: center;
}

.position-filter .el-dropdown {
    width: 300px;
    border: 1px solid #DCDFE6;
    border-radius: 7px;
    height: 30px;
    background: white;
}

.position-filter .title {
    color: rgb(41, 41, 41);
    font-size: 14px;
    margin-right: 5px;
}

.position-filter .el-dropdown-link {
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