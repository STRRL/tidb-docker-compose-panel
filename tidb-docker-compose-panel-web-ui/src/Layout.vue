<template>
  <div>
    <el-container>
      <el-header>
        <h1>TiDB docker compose panel</h1>
      </el-header>
      <el-button
        type="text"
        @click="backToHome"
      >Back to home</el-button>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "layout",
  methods: {
    backToHome () {
      this.$router.push({ name: "home" });
    }
  }
}
</script>
<style>
</style>
