<template>
  <div class="home">
    <h1>TiDB Clusters</h1>
    <cluster-table/>
  </div>
</template>

<script>
// @ is an alias to /src
import ClusterTable from '@/components/CluterTable';

export default {
  name: 'home',
  components: {
    ClusterTable
  }
}
</script>
