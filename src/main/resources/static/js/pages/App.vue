<template>
    <v-app>
        <v-toolbar app>
            <v-toolbar-title>Sarafan</v-toolbar-title>
            <v-btn v-if="profile" flat :disabled='$route.path==="/"' @click='showMessages'>
                Messages
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn v-if="profile" flat :disabled='$route.path==="/profile"' @click='showProfile'>
                {{profile.name}}
            </v-btn>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-toolbar>
        <v-content>
            <v-container v-if="profile">
                <messages-list />
            </v-container>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import { addHandler } from 'util/ws'
    import { mapState, mapMutations } from 'vuex'

    export default {
        computed: mapState(['profile']),
        methods: {
            ...mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
            showMessages() {
                this.$router.push('/')
            },
            showProfile() {
                this.$router.push('/profile')
            },
        },
        created() {
            addHandler(data => {
                if (data.objectType === "MESSAGE") {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateMessageMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removeMessageMutation(data.body)
                            break
                        default:
                            console.error(`Event type is unknown: "${data.eventType}"`)
                    }
                } else {
                    console.error(`Object type is unknown: "${data.objectType}"`)
                }
            })
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('/auth')
            }
        }
    }
</script>

<style>
</style>