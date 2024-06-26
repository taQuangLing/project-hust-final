<template>
    <div @click.self="closePopup">
        <div class="add-branch">
            <h1>THÊM CHI NHÁNH</h1>
            <div class="info">
                <div class="name flex">
                    <span>Tên chi nhánh</span>
                    <el-input v-model="name"></el-input>
                </div>
                <div class="address flex">
                    <span>Địa chỉ</span>
                    <el-input v-model="address"></el-input>
                </div>
                <div class="city flex">
                    <span>Tỉnh/Thành phố</span>
                    <el-input v-model="city"></el-input>
                </div>
                <div class="district flex">
                    <span>Quận/huyện</span>
                    <el-input v-model="district"></el-input>
                </div>

                <BranchSelect
                    @branchSelected="selectBranch"
                    class="branch-filter"
                    :branchSelectedId="branchSelectedId"
                />
                <div class="img">
                    <span class="title">Ảnh</span>
                    <el-upload
                        class="upload-demo"
                        list-type="picture"
                        :auto-upload="false"
                        :limit="1"
                        ref="upload"
                        :on-change="handleChange"
                        :on-error="onError"
                        :on-success="onSuccess"
                        :data="data"
                        action="https://api.cloudinary.com/v1_1/dn1pbep3e/upload"
                    >
                        <el-button size="small" type="primary"
                            >Click to upload</el-button
                        >
                        <div slot="tip" class="el-upload__tip">
                            jpg/png files with a size less than 500kb
                        </div>
                    </el-upload>
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
import BranchSelect from './BranchSelect.vue';
import PositionSelect from './PositionSelect.vue';
import axios from 'axios';

export default {
    components: {
        BranchSelect,
        PositionSelect,
    },
    data() {
        return {
            branchSelectedId: 0,
            positionSelectedId: 0,
            name: '',
            phoneNumber: '',
            email: '',
        };
    },
    methods: {
        closePopup() {
            this.$emit('close');
        },
        selectPosition(param) {
            this.positionSelectedId = param.id;
        },
        selectBranch(param) {
            this.branchSelectedId = param.id;
        },
        save() {
            if (
                this.name == '' ||
                this.phoneNumber == '' ||
                this.branchSelectedId == 0 ||
                this.positionSelectedId == 0
            ) {
                this.$message({
                    message: 'Vui lòng điền đầy đủ thông tin',
                    type: 'warning',
                });
                return;
            }
            axios
                .post(this.$store.state.baseUrl + '/auth/v1/register', {
                    name: this.name,
                    phone: this.phoneNumber,
                    email: this.email,
                    branchId: this.branchSelectedId,
                    positionId: this.positionSelectedId,
                    createdBy: localStorage.getItem('id'),
                })
                .then(res => {
                    if (res.data.code != 2000) {
                        this.$message({
                            message: res.data.description,
                            type: 'error',
                        });
                        return false;
                    }
                    this.$message({
                        message: 'Đăng kí thành công',
                        type: 'success',
                    });
                    this.$emit('userCreated');
                    return true;
                })
                .catch(err => {
                    console.log(err);
                });
        },
    },
};
</script>
