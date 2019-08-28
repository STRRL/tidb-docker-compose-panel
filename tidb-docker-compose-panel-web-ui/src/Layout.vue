<template>
  <div>
    <el-container>
      <el-header>
        <h1>TiDB docker compose panel</h1>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "layout"
}
</script>
<style>
</style>
