<template>
  <div>
    <div class="cluster-container">
      <div class="cluster-table">
        <cluster-card
          v-for="cluster in clusters"
          :key="cluster.name"
          :name="cluster.name"
          :nTidb="cluster.tidbs.length"
          :nTikv="cluster.tikvs.length"
          :nPd="cluster.pds.length"
          @purge="purgeCluster"
        />
        <cluster-card-add />

      </div>
    </div>
  </div>
</template>

<script>
import ClusterCard from '@/components/ClusterCard';
import ClusterCardAdd from '@/components/ClusterCardAdd';
import { list, purge } from '@/server';

export default {
  name: 'clusterTable',
  components: { ClusterCard, ClusterCardAdd },
  data () {
    return {
      clusters: []
    };
  },
  mounted () {
    list().then(resp => {
      this.clusters = resp.data;
    }).catch(error => {
      this.$message({
        message: error.response.data.message,
        type: 'warning'
      });
    })
  },
  methods: {
    purgeCluster (clusterName) {
      purge(clusterName).then(resp => {
        this.$message(`Cluster ${resp.data.name} will be purged.\nPlease manually refresh this page later.`);
      }).catch(error => {
        this.$message({
          message: error.response.data.message,
          type: 'warning'
        });
      });
    }
  }
}
</script>

<style>
.cluster-container {
  display: grid;
  grid-template-columns: 10% 80% 10%;
}

.cluster-table {
  grid-column: 2;

  display: grid;
  grid-column-gap: 10px;
  grid-row-gap: 15px;
  grid-template-columns: 25% 25% 25% 25%;
}
@media screen and (max-width: 1300px) {
  .cluster-table {
    grid-template-columns: 50% 50%;
  }
}
@media screen and (max-width: 670px) {
  .cluster-table {
    grid-template-columns: 100%;
  }
}
</style> 