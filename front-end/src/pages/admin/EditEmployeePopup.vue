<template>
    <div @click.self="closePopup">
        <div class="edit-employee">
            <h1>THÔNG TIN CHI TIẾT</h1>
            <div class="info">
                <div class="code">
                    <span>Mã nhân viên</span>
                    <el-input v-model="employee.code" :disabled="true"></el-input>
                </div>
                <div class="name">
                    <span>Tên nhân viên</span>
                    <el-input v-model="name"></el-input>
                </div>
                <BranchSelect @branchSelected="selectBranch" class="selected" :branchSelectedId="branchSelectedId" />
                <div class="phone">
                    <span>Số điện thoại</span>
                    <el-input v-model="phone"></el-input>
                </div>
                <div class="email">
                    <span>Email</span>
                    <el-input v-model="email"></el-input>
                </div>
                <PositionSelect @positionSelected="selectPosition" class="selected"
                    :positionSelectedId="positionSelectedId" />
                <div class="created-at">
                    <span>Được tạo ngày</span>
                    <el-input v-model="employee.createdAt" :disabled="true"></el-input>
                </div>
                <div class="status">
                    <span>Trạng thái</span>
                    <div class="status-info">
                        <el-input v-model="employee.statusStr" :disabled="true"></el-input>
                        <el-switch v-model="employee.status">
                        </el-switch>
                    </div>
                </div>
            </div>
            <div class="action">
                <button class="close" @click="closePopup">Đóng</button>
                <button class="save" @click="save">Lưu</button>
            </div>
        </div>
    </div>
</template>

<script>
import BranchSelect from '@/components/BranchSelect.vue';
import PositionSelect from '@/components/PositionSelect.vue';
import axios from 'axios';

export default {
    components: {
        BranchSelect,
        PositionSelect
    },
    props: ['employeeId'],
    data() {
        return {
            branchSelectedId: 0,
            positionSelectedId: 0,
            name: '',
            phone: '',
            email: '',
            employee: {}
        }
    },
    methods: {
        closePopup() {
            this.$emit("close");
        },
        selectPosition(param) {
            this.positionSelectedId = param.id;
        },
        selectBranch(param) {
            this.branchSelectedId = param.id;
        },
        getEmployee() {
            axios.get(this.$store.state.baseUrl + "/admin/v1/employees/" + this.employeeId + "?userId=" + localStorage.getItem('id'), {
                headers: { Authorization: 'Bearer ' + localStorage.getItem('user') }
            }).then(res => {
                this.employee = res.data.data;
                console.log(this.employee);
                this.name = this.employee.name;
                this.phone = this.employee.phone;
                this.email = this.employee.email;
                this.branchSelectedId = this.employee.branchId,
                this.positionSelectedId = this.employee.positionId
            }).catch(err => {
                console.log(err);
            })
        },
        save() {
            if (this.name == '' || this.phoneNumber == '' || this.branchSelectedId == 0 || this.positionSelectedId == 0) {
                this.$message({
                    message: 'Vui lòng điền đầy đủ thông tin',
                    type: 'warning'
                });
                return;
            }
            axios.put(this.$store.state.baseUrl + "/admin/v1/employees/" + this.employee.id + "?userId=" + localStorage.getItem('id'), {
                name: this.name,
                phone: this.phone,
                email: this.email,
                branchId: this.branchSelectedId,
                positionId: this.positionSelectedId,
                status: this.employee.status ? 1 : 0
            },
            {
                headers: { Authorization: 'Bearer ' + localStorage.getItem('user') }
            }
        ).then(res => {
                if (res.data.code != 2000) {
                    this.$message({
                        message: res.data.description,
                        type: 'error'
                    });
                    return false;
                }
                this.$message({
                    message: 'Cập nhật thông tin thành công',
                    type: 'success'
                });
                this.$emit("userEdited");
                return true;
            }).catch(err => {
                console.log(err);

            })
        }
    },
    mounted() {
        this.getEmployee();
    }

}

</script>

<style></style>