<template>
  <div>
    <div class="popup-overlay" @click.self="closePopup">
      <div class="popup-content">
        <!-- Nội dung của popup -->
        <div class="header">
          <label for="">Sửa ghi chú</label>
          <!-- <i class="el-icon-close" @click="closePopup"></i> -->
        </div>
        <el-input type="textarea" :rows="2" placeholder="Thêm ghi chú" v-model="note">
        </el-input>
        <el-button class="submit" @click="save">Lưu</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['noteEdit'],
  data() {
    return {
      note: this.noteEdit
    }
  },
  methods: {
    closePopup() {
      this.$emit("close", false);
    },
    save() {
      console.log(this.note);
      this.$emit("save", this.note);
    }
  }
}
</script>