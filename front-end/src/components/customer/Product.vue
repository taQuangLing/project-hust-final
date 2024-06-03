<template>
  <div class="home">
    <div class="tab-container">
      <div :class='{"tab-item": true, "tab-item-active": activeTab === 0}' @click="activeTab = 0">
        <i
          class="el-icon-menu"
          style="font-size: 50px; color: #acacac; margin-bottom: 3px"
        ></i>
        <label>Tất cả</label>
      </div>
      <div
        v-for="(category, index) in categories"
        :key="index"
        :class='{"tab-item": true, "tab-item-active": activeTab === index + 1}'
        @click="activeTab = index + 1"
      >
        <img :src="category.image" alt="category.name" />
        <label>{{ category.name }}</label>
      </div>
    </div>
    <div class="content-container">
      <div class="drink" v-for="(drink, index) in drinks" :key="index">
        <div class="info">
          <img :src="drink.image" alt="" />
          <div class="info-detail">
            <label>{{ drink.name }}</label>
            <p>{{ drink.sumary }}</p>
            <span>{{ drink.price }}</span>
          </div>
        </div>
        <i class="el-icon-plus" @click="showModal = true"></i>
      </div>
      <AddProductPopup 
        :isPopupVisible="showModal" 
        @close="showModal = false"
      /> 
    </div>
  </div>
  
</template>

<script>
import AddProductPopup from './AddProductPopup.vue';


export default {
  components: {
    AddProductPopup
  },
  data() {
    return {
      activeTab: 0,
      showModal: false,
      categories: [
        {
          name: "Trà",
          image:
            "https://hoanmy.com/wp-content/uploads/2023/07/cong-dung-cua-tra-1-1200x900.png",
        },
        {
          name: "Cafe",
          image:
            "https://oola.vn/wp-content/uploads/2023/03/14.-Ca-phe-nau.png",
        },
        {
          name: "Bánh ngọt",
          image:
            "https://bizweb.dktcdn.net/100/438/465/files/banh-ngot-5.jpg?v=1669955176914",
        },
        {
          name: "Bánh ngọt",
          image:
            "https://bizweb.dktcdn.net/100/438/465/files/banh-ngot-5.jpg?v=1669955176914",
        },
        {
          name: "Bánh ngọt",
          image:
            "https://bizweb.dktcdn.net/100/438/465/files/banh-ngot-5.jpg?v=1669955176914",
        },
        {
          name: "Bánh ngọt",
          image:
            "https://bizweb.dktcdn.net/100/438/465/files/banh-ngot-5.jpg?v=1669955176914",
        },
        {
          name: "Bánh ngọt",
          image:
            "https://bizweb.dktcdn.net/100/438/465/files/banh-ngot-5.jpg?v=1669955176914",
        },
        // Add more categories as needed
      ],
      drinks: [
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        {
          name: "Trà chanh xí muội ",
          sumary: "Trà chanh tươi thêm xí muội ",
          price: "24,300 đ",
          image: "https://honglam.vn/pic/news/3%20(15).png",
        },
        // Add more drinks as needed
      ],
    };
  },
};
</script>

<style scoped>
@import "../../mixin/addProductPopup.css"
.home {
  height: 100%;
  /* padding-top: 10px; */
  display: grid;
  grid-template-rows: 110px 1fr;
}
.tab-container {
  display: flex;
  width: 100%;
  display: flex;
  overflow-x: auto;
  white-space: nowrap;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
  flex: 0 0 auto;
  margin: 10px 0 5px 0;
}

.tab-container::-webkit-scrollbar {
  /* Chrome, Safari and Opera */
  display: none;
}

.tab-item {
  display: flex;
  height: auto;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  margin-right: 20px;
  padding: 5px;
  border-radius: 10px;
  color: #727272;
  /* box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2); */
}
.tab-item-active {
  font-weight: 500;
  color: #fc9332;
}
.tab-item-active label {
  font-size: 14px !important;
}

.tab-item-active img {
  width: 60px !important;
  height: 60px !important;
  box-shadow: 0px 7px 13px rgba(0, 0, 0, 0.534);
}

.tab-item img {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  margin-bottom: 3px;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.336);
}

.tab-item label {
  font-size: 12px;
  font-family: "Sarabun";
  flex: 0 0 auto;
}

.content-container {
  flex-grow: 1; /* This will make it take up the remaining space */
  overflow-y: auto; /* This will make it scrollable if the content overflows */
}
.info img {
  width: 70px;
  height: 70px;
  border-radius: 5px;
  flex: 0 0 70px;
}
.drink {
  display: flex;
  justify-content: space-between;
  background: #ffffff;
  border-radius: 5px;
  margin-bottom: 25px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
}
.info {
  display: flex;
  flex: 1;
  padding: 10px;
}
.info-detail {
  text-align: left;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex: 1;
  padding: 0 15px 0 13px;
}
.info-detail label {
  color: #451717;
  font-size: 17px;
  font-weight: 700;
  margin-bottom: 3px;
}
.info-detail p {
  margin: 0;
  color: #696969;
  font-size: 12px;
  margin-bottom: 3px;
}
.info-detail span {
  color: #ff902a;
  font-size: 15px;
  font-weight: bold;
}
.el-icon-plus {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 21px;
  flex: 0 0 60px;
  height: 35px;
  font-weight: bold;
  color: #ffffff;
  /* text-shadow: -0.3px -0.3px 0 #ffffff, 0.3px -0.3px 0 #ffffff,
    -0.3px 0.3px 0 #ffffff, 0.3px 0.3px 0 #ffffff; */
  background: #ff902a;
  border-bottom-left-radius: 14px;
  border-top-right-radius: 5px;
  -webkit-text-stroke: 1px white;
}
</style>
