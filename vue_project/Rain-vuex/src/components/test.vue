<template>
  <div id="app" class="hello">
    <ueditor></ueditor>
  </div>
</template>

<script>
  import ueditor from './ueditor'

  export default{
      name: 'hello',
      components: {ueditor},
      data(){
        return {
        }
      },
      methods: {
      }
  }

</script>