<template>
    <div @click.self="closePopup">
        <div class="add-employee">
            <h1>THÊM CHI NHÁNH</h1>
            <div class="info">
                
            </div>
            <div class="action">
                <button class="close" @click="closePopup">Đóng</button>
                <button class="save" @click="save">Lưu</button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    
}
</script>