<template>
  <div>
    <h1>Create new cluster.</h1>
    <cluster-form />
  </div>
</template>

<script>
import ClusterForm from '../components/ClusterForm';
export default {
  name: 'createCluster',
  components: { ClusterForm }
}
</script>

<style>
</style>