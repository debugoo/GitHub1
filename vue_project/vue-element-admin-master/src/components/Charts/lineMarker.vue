<template>
  <div :class="className" :id="id" :style="{height:height,width:width}"></div>
   <el-button @click="danji">圆角按钮</el-button>
  
  <!-- 
    写一些html,element-ui

    布局用
     <el-row> 一行
    <el-col :span="6"> </el-col> 列y 一列24 
   </el-row>
   -->
</template>

<script>
import echarts from 'echarts' //引用插件,组件
import '../../def.js' //引用文件
import {mapGetters,mapActions} from 'vuex'

export default {
  data() {
    return {//定义 变量
      chart: null
    }
  },
  props: {
    className: { //父组件参数
      type: String,
      default: 'chart'
    },
   
  },
  computed:{
      ...mapGetters([
          'num'
      ]),
      
  },
  created(){//完成了 data 数据的初始化，el没有, 可以事先初始化变量 

  },
  mounted() { //完成挂载
    this.initChart() //初始化 调用
  },
  methods: { //方法定义部分
    initChart() {
    
    },
    danji(){ //单击 方法

    }
  },
  destroyed () { //组件别销毁 
    
  }
}
</script>
<style scoped>
/*
 scoped: 局部样式
 
 */
</style>
