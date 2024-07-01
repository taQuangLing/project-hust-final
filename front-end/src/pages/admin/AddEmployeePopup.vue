<template>
    <div @click.self="closePopup">
        <div class="add-employee">
            <h1>ĐĂNG KÍ NHÂN VIÊN MỚI</h1>
            <div class="info">
                <div class="name">
                    <span>Tên nhân viên</span>
                    <el-input v-model="name"></el-input>
                </div>
                <BranchSelect @branchSelected="selectBranch" class="branch-filter" :branchSelectedId="branchSelectedId" />
                <div class="phone">
                    <span>Số điện thoại</span>
                    <el-input v-model="phoneNumber"></el-input>
                </div>
                <div class="email">
                    <span>Email</span>
                    <el-input v-model="email"></el-input>
                </div>
                <PositionSelect @positionSelected="selectPosition" class="position-filter"
                    :positionSelectedId="positionSelectedId" />
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
    data() {
        return {
            branchSelectedId: 0,
            positionSelectedId: 0,
            name: '',
            phoneNumber: '',
            email: '',
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
        save() {
            if (this.name == '' || this.phoneNumber == '' || this.branchSelectedId == 0) {
                this.$message({
                    message: 'Vui lòng điền đầy đủ thông tin',
                    type: 'warning'
                });
                return;
            }
            axios.post(this.$store.state.baseUrl + "/auth/v1/register", {
                name: this.name,
                phone: this.phoneNumber,
                email: this.email,
                branchId: this.branchSelectedId,
                positionId: this.positionSelectedId,
                createdBy: localStorage.getItem('id'),
                role: 'USER'
            }).then(res => {
                if (res.data.code != 2000) {
                    this.$message({
                        message: res.data.description,
                        type: 'error'
                    });
                    return false;
                }
                this.$message({
                    message: 'Đăng kí thành công',
                    type: 'success'
                });
                this.$emit("userCreated");
                return true;
            }).catch(err => {
                console.log(err);

            })
        }
    },

}

</script>

<style></style>