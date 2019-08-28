<template>
  <div>
    <el-row>
      <el-col
        :span="12"
        :offset="6"
      >
        <el-form
          ref="cluster"
          :model="cluster"
          label-width="180px"
        >
          <el-form-item label="Cluster name">
            <el-input v-model="cluster.name"></el-input>
          </el-form-item>
          <hr>
          <el-row :gutter="20">
            <el-col :span="8">
              <dir>TiDB instance: {{ cluster.tidbs.length }} </dir>
            </el-col>
            <el-col :span="8">
              <dir>TiKV instance: {{ cluster.tikvs.length }} </dir>
            </el-col>
            <el-col :span="8">
              <dir>PD instance: {{ cluster.pds.length }} </dir>
            </el-col>
          </el-row>

          <hr>
          <el-row :gutter="20">
            <el-col :span="8">
              <!-- eslint-disable-next-line vue/require-v-for-key -->
              <div v-for="tidb in cluster.tidbs">
                <h3>TiDB Instance {{ tidb.name }}</h3>
                <el-form-item label="TiDB instance name">
                  <el-input v-model="tidb.name"></el-input>
                </el-form-item>
                <el-form-item label="TiDB image version">
                  <el-input v-model="tidb.version"></el-input>
                </el-form-item>
                <el-form-item label="Expose ports">
                  <el-switch v-model="tidb.exposed"></el-switch>
                </el-form-item>
                <el-form-item
                  label="Expose servre port"
                  v-if="tidb.exposed"
                >
                  <el-input v-model="tidb.exposeServerPort"></el-input>
                </el-form-item>
                <el-form-item
                  label="Expose statis port"
                  v-if="tidb.exposed"
                >
                  <el-input v-model="tidb.exposeStatusPort"></el-input>
                </el-form-item>
                <el-button
                  type="danger"
                  @click="removeTidb(tidb)"
                >Remove this TiDB instance</el-button>
                <hr>
              </div>
              <el-button
                type="primary"
                @click="newTidb"
              >Add new TiDB instance</el-button>
            </el-col>
            <el-col :span="8">
              <!-- eslint-disable-next-line vue/require-v-for-key -->
              <div v-for="tikv in cluster.tikvs">
                <h3>TiKV Instance {{ tikv.name }}</h3>
                <el-form-item label="TiKV instance name">
                  <el-input v-model="tikv.name"></el-input>
                </el-form-item>
                <el-form-item label="TiKV image version">
                  <el-input v-model="tikv.version"></el-input>
                </el-form-item>
                <el-button
                  type="danger"
                  @click="removeTikv(tikv)"
                >Remove this TiKV instance</el-button>
                <hr>
              </div>
              <el-button
                type="primary"
                @click="newTikv"
              >Add new TiKV instance</el-button>
            </el-col>
            <el-col :span="8">
              <!-- eslint-disable-next-line vue/require-v-for-key -->
              <div v-for="pd in cluster.pds">
                <h3>PD Instance {{ pd.name }}</h3>
                <el-form-item label="PD instance name">
                  <el-input v-model="pd.name"></el-input>
                </el-form-item>
                <el-form-item label="PD image version">
                  <el-input v-model="pd.version"></el-input>
                </el-form-item>
                <el-button
                  type="danger"
                  @click="removePd(pd)"
                >Remove this PD instance</el-button>
                <hr>
              </div>
              <el-button
                type="primary"
                @click="newPd"
              >Add new PD instance</el-button>
            </el-col>
          </el-row>
          <hr>
          <el-form-item label="Prometheus expose port">
            <el-input v-model="cluster.prometheus.exposePort"></el-input>
          </el-form-item>
          <hr>
          <el-form-item label="Grafana expose port">
            <el-input v-model="cluster.grafana.exposePort"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="onSubmit"
            >Create</el-button>
            <el-button @click="cancel">Cancel</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { startup } from '@/server';
import { setTimeout } from 'timers';

export default {
  name: "clusterForm",
  data () {
    return {
      cluster: {
        name: "new_cluster",
        pds: [
          {
            name: "pd0",
            version: "latest"
          },
          {
            name: "pd1",
            version: "latest"
          },
          {
            name: "pd2",
            version: "latest"
          },
        ],
        tidbs: [
          {
            name: "tidb0",
            version: "latest",
            exposeServerPort: 4000,
            exposeStatusPort: 10080,
            exposed: true
          }
        ],
        tikvs: [
          {
            name: "tikv0",
            version: "latest"
          },
          {
            name: "tikv1",
            version: "latest"
          },
          {
            name: "tikv2",
            version: "latest"
          }
        ],
        prometheus: {
          exposePort: 9090
        },
        grafana: {
          exposePort: 3000
        },
      },
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      }
    }
  },
  methods: {
    newTidb () {
      this.cluster.tidbs.push({
        name: `tidb${this.cluster.tidbs.length}`,
        version: "latest",
        exposeServerPort: 4000 + this.cluster.tidbs.length,
        exposeStatusPort: 10080 + this.cluster.tidbs.length,
        exposed: this.cluster.tidbs.length == 0
      });
    },
    removeTidb (tidb) {
      const index = this.cluster.tidbs.indexOf(tidb)
      if (index > -1) {
        this.cluster.tidbs.splice(index, 1);
      }
    },
    newTikv () {
      this.cluster.tikvs.push({
        name: `tikv${this.cluster.tikvs.length}`,
        version: "latest"
      });
    },
    removeTikv (tikv) {
      const index = this.cluster.tikvs.indexOf(tikv)
      if (index > -1) {
        this.cluster.tikvs.splice(index, 1);
      }
    },
    newPd () {
      this.cluster.pds.push({
        name: `tikv${this.cluster.pds.length}`,
        version: "latest"
      });
    },
    removePd (pd) {      const index = this.cluster.pds.indexOf(pd)
      if (index > -1) {
        this.cluster.pds.splice(index, 1);
      }
    },
    onSubmit () {
      // eslint-disable-next-line
      startup(this.cluster).then(ignore => {
        this.$message("Cluster creating. Please refresh cluster table later.");
        const vueThis = this;
        setTimeout(() => { vueThis.$router.push({ name: "home" }); }, 1000);
      }).catch(error => {
        this.$message({
          message: error.response.data.message,
          type: 'warning'
        });
      })
    },
    cancel () {
      this.$router.push({ name: "home" });
    }
  }
}
</script>

<style>
</style>