export default function authHeader() {
    let user = JSON.parse(localStorage.getItem('user'));

    if (user && user.token) {
        header.Authorization = 'Bearer ' + user.token;
    }
    return header;
}