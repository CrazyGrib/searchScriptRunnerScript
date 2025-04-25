import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

String fileContents = new File('scriptRegistry.json').getText()

JsonSlurper jsonSlurper = new JsonSlurper()
def jsonObject = jsonSlurper.parseText(fileContents)

StringWriter writer = new StringWriter()
def build = new MarkupBuilder(writer)

String textSearch = "ComponentAccessor"

build.html {
    body {
        table(border: "1px", width: "100%") {
            tr {
                td("location")
                td("script")
                td("link")
            }
            jsonObject.output.workflows.each { workflow ->
                if (workflow.active == true && workflow.draft == false) {
                    workflow.transitions.each { transition ->
                        transition.configuredItems.each { configuredItem ->
                            configuredItem.scripts.each { script ->
                                if (script.scriptFile == null && script.inlineScript.toString().contains(textSearch)) {
                                    tr {
                                        td("Workflow / ${workflow.name} / ${transition.name} / ${configuredItem.name}")
                                        td(script.inlineScript)
                                        td("https://BaseUrl${transition.href}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            jsonObject.output.listeners.each { listener ->
                listener.scripts.each { script ->
                    if (script.scriptFile == null && script.inlineScript.toString().contains(textSearch)) {
                        tr {
                            td("listener / ${listener.name}")
                            td(script.inlineScript)
                            td("https://BaseUrl${listener.link}")
                        }
                    }
                }
            }
            jsonObject.output.fields.each { field ->
                field.scripts.each { script ->
                    if (script.scriptFile == null && script.inlineScript.toString().contains(textSearch)) {
                        tr {
                            td("field / ${field.name}")
                            td(script.inlineScript)
                            td("https://BaseUrl${field.link}")
                        }
                    }
                }
            }
            jsonObject.output.endpoints.each { endpoint ->
                endpoint.scripts.each { script ->
                    if (script.scriptFile == null && script.inlineScript.toString().contains(textSearch)) {
                        tr {
                            td("endpoint / ${endpoint.name}")
                            td(script.inlineScript)
                            td("https://BaseUrl${endpoint.link}")
                        }
                    }
                }
            }
            jsonObject.output.behaviours.each { behaviour ->
                behaviour.scripts.each { script ->
                    if (script.scriptFile == null && script.inlineScript.toString().contains(textSearch)) {
                        tr {
                            td("behaviour / ${behaviour.name}")
                            td(script.inlineScript)
                            td("https://BaseUrl${behaviour.link}")
                        }
                    }
                }
            }
            jsonObject.output.fragments.each { fragment ->
                fragment.scripts.each { script ->
                    if (script.scriptFile == null && script.inlineScript.toString().contains(textSearch)) {
                        tr {
                            td("fragment / ${fragment.name}")
                            td(script.inlineScript)
                            td("https://BaseUrl${fragment.link}")
                        }
                    }
                }
            }
            jsonObject.output.jobs.each { job ->
                job.scripts.each { script ->
                    if (script.scriptFile == null && script.inlineScript.toString().contains(textSearch)) {
                        tr {
                            td("job / ${job.name}")
                            td(script.inlineScript)
                            td("https://BaseUrl${job.link}")
                        }
                    }
                }
            }
        }
    }
}

new File('search.html').withWriter('utf-8') {
    it.writeLine writer.toString()
}