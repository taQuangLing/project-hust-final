<template>
    <div v-loading="loading" class="authen"></div>
</template>

<script>
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';

export default {
    data() {
        return {
            loading: true
        }
    },
    mounted() {
        const jwt = localStorage.getItem('user');
        if (jwt == null) {
            this.genToken();
        } else {
            axios.put(this.$store.state.baseUrl + "/auth/v1/check-token", {
                jwt: jwt
            }).then(res => {
                if (res.data.code != 4010) {

                } else
                    this.$router.push('/');
            }).catch(err => {
                console.log(err);
                this.$router.push('/error');
            })
        }
        this.loading = false;
    },
    methods: {
        genToken() {
            const code = this.$route.query.code;
            const tableNumber = this.$route.query.tableNumber ? this.$route.query.tableNumber : 0;

            axios.get(this.$store.state.baseUrl + '/auth/v1/guest-token?' +
                'code=' + code,
                '&tableNumber=' + tableNumber
            ).then(response => {
                console.log("authen");
                const jwt = JSON.stringify(response.data.data.jwt).replace(/\"/g, "");
                const id = jwtDecode(jwt).id;
                const branchId = jwtDecode(jwt).branchId;
                const tableNumber = jwtDecode(jwt).tableNumber ? jwtDecode(jwt).tableNumber : 0;
                localStorage.setItem('user', jwt);
                localStorage.setItem('id', id);
                localStorage.setItem('branchId', branchId);
                localStorage.setItem('tableNumber', tableNumber);
                this.$router.push('/');
            }).catch(error => {
                console.log(error);
                this.$router.push('/error');
            })
        }
    }
}
</script>

<style scoped>
div {
    width: 100vw;
    height: 100vh;
    background: rgba(27, 27, 27, 0.493);
}
</style>