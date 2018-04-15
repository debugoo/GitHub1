<template>

  <div class="center">

    <br/><br/>

    <table id="nonFixedDemo" width="500" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <th>header</th>
        <th>header</th>
        <th>header</th>
      </tr>
      <tr>
        <td class="left">cell</td>
        <td>cell</td>
        <td class="right">cell</td>
      </tr>
      <tr>
        <td class="left">cell</td>
        <td>cell</td>
        <td class="right">cell</td>
      </tr>
      <tr>
        <td class="left bottom">cell</td>
        <td class="bottom">cell</td>
        <td class="bottom right">cell</td>
      </tr>
    </table>

    <br/>

    <br/><br/>

  </div>
</template>

<script type="es6">
import { mapGetters } from "vuex";
import $ from 'jquery';
// import  "../../../static/js/jquery";
import  "../../../static/js/colResizable";
import  "../../../static/css/main.css";

export default {
  data() {
    return {
      rangeValue: 0
    };
  },
  filters: {},
  computed: {},
  created () {
    setTimeout(() => {
      $("#nonFixedDemo").colResizable({
        liveDrag:true, 
        gripInnerHtml:"<div class='grip'></div>", 
        draggingClass:"dragging", 
        fixed:false,
      });	
    }, 0);
  },
  methods: {}
};
</script>
