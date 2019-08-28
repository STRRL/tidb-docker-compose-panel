<template>
  <div>
    <el-card class="cluster-card">
      <div
        slot="header"
        class="clearfix"
      >
        <span>Cluster: {{ name }}</span>
        <el-button
          style="float: right; padding: 3px 0"
          @click="purge"
          type="danger"
        >Purge</el-button>
      </div>
      <div>
        <div>
          TiDB instance: {{ nTidb }}
        </div>
        <div>
          TiKV instance: {{ nTikv }}
        </div>
        <div>
          PD instance: {{ nPd }}
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "clusterCard",
  data () {
    return {};
  },
  props: ["name", "nTidb", "nTikv", "nPd"],
  methods: {
    purge () {
      this.$emit("purge", this.name);
    }
  }
}
</script>

<style>
.cluster-card {
  min-height: 167px;
}
</style>