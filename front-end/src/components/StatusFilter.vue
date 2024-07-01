<template>
    <div class="status-filter">
        <span>Trạng thái</span>
        <el-dropdown @command="handleCommandStatus">
            <span class="el-dropdown-link">
                {{ status }}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
                <!-- <el-dropdown-item command="-1" >{{ "<none>" }}</el-dropdown-item> -->
                <el-dropdown-item command="1">{{ "Hoạt động" }}</el-dropdown-item>
                <el-dropdown-item command="0">{{ "Không hoạt động" }}</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
export default {
    props: ['statusSelected'],
    computed: {
        status() {
            return this.statusSelected;
        }
    },
    methods: {
        handleCommandStatus(command) {
            switch (command) {
                case "0":
                    this.$emit('status', "Không hoạt động");
                    break;
                case "1":
                    this.$emit('status', "Hoạt động");
                    break;
            }
        },
    }
}
</script>

<style>
.status-filter .el-dropdown {
    width: 150px;
    border: 1px solid #DCDFE6;
    border-radius: 7px;
    height: 28px;
    background: white;
}

.status-filter .el-dropdown-link{
    cursor: pointer;
    color: #409EFF;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
    margin: 0 10px 0 10px;
    color: rgb(41, 41, 41);
}

.status-filter {
    margin-left: 20px;
    display: flex;
    align-items: center;
}

.status-filter span {
    color: rgb(41, 41, 41);
    font-size: 14px;
    margin-right: 5px;
}
</style>