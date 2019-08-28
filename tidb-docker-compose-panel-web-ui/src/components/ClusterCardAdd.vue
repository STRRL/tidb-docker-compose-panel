<template>
  <div class="cluster-card-add-container">
    <el-card class="cluster-card-add">
      <div
        slot="header"
        class="clearfix"
      >
        <span>Create cluster</span>
      </div>
      <el-button @click="create">New</el-button>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "clusterCardAdd",
  methods: {
    create () {
      this.$router.push({ name: "create" });
    }
  }
}
</script>

<style>
.cluster-card-add-container {
  height: 100%;
  min-height: 167px;
}
.cluster-card-add {
  height: 100%;
  min-height: 167px;
  position: relative;
}
</style>