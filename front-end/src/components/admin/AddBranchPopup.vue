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
                    <el-input v-model="town"></el-input>
                </div>

                <ManagerSelect
                    @managerSelected="selectManager"
                    class="manager-filter"
                    :managerSelectedId="managerSelectedId"
                />
                <div class="img">
                    <span class="title">Logo</span>
                    <el-upload
                        class="upload-demo"
                        list-type="picture"
                        :auto-upload="false"
                        :limit="1"
                        ref="upload"
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
import ManagerSelect from './ManagerSelect.vue';
import axios from 'axios';

export default {
    components: {
        BranchSelect,
        ManagerSelect,
    },
    data() {
        return {
            managerSelectedId: 0,
            name: '',
            phoneNumber: '',
            email: '',
            address: '',
            city: '',
            town: '',
        };
    },
    computed:{
        data() {
            return {
                upload_preset: 'bhdixbmd',
                api_key: '231664969325537'
            }
        }
    },
    methods: {
        closePopup() {
            this.$emit('close');
        },
        selectManager(param) {
            this.managerSelectedId = param.id;
        },
        save() {
            if (this.name == "" || this.address == "") {
                this.$message.warning("Vui lòng nhập đầy đủ thông tin");
                return false;
            }
            if (this.$refs.upload.uploadFiles.length == 0) {
                this.$message.warning("Vui lòng chọn ảnh");
                return false;
            }
            if (this.$refs.upload.uploadFiles[0].status == "ready")this.$refs.upload.submit();
                     
        },
        onError() {
            this.$message.error("Lỗi khi upload ảnh");
        },
        onSuccess(e) {
            this.img = e.url;
            axios.post(this.$store.state.baseUrl + "/admin/v1/branches", 
            {
                name: this.name,
                address: this.address,
                town: this.town,
                city: this.city,
                createdBy: localStorage.getItem("id"),
                logo: this.img,
                managerId: this.managerSelectedId
            },
                {
                    headers: { Authorization: "Bearer " + localStorage.getItem("user") }
                },
            ).then(res => {
                if (res.data.code != 2000){
                    this.$message.error(res.data.description);
                    return;
                }
                this.$message.success("Thêm chi nhánh thành công");
                this.$emit('branchCreated');
                return;
            }).catch(err => {
                console.log(err);
            });
        },
    },
};
</script>
