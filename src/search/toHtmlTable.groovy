import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

//path to json file
String fileContents = new File('scriptRegistry.json').getText()

JsonSlurper jsonSlurper = new JsonSlurper()
def jsonObject = jsonSlurper.parseText(fileContents)

StringWriter writerWorkflow = new StringWriter()
def buildWorkflow = new MarkupBuilder(writerWorkflow)

buildWorkflow.html {
    body {
        table(border: "1px", width: "100%") {
            tr {
                td("name")
                td("transitions")
            }
            jsonObject.output.workflows.each { workflow ->
                tr {
                    td(workflow.name)
                    td {
                        workflow.transitions.each { transition ->
                            table(border: 1) {
                                tr {
                                    td(transition.name)
                                    td {
                                        transition.configuredItems.each { configuredItem ->
                                            table(border: 1) {
                                                tr {
                                                    td(configuredItem.name)
                                                    td {
                                                        table(border: 1) {
                                                            tr {
                                                                configuredItem.scripts.each { script ->
                                                                    // Если надо ссылку
                                                                    //td("https://BaseUrl${transition.href}")
                                                                    if (script.scriptFile != null) {
                                                                        td("scriptFile")
                                                                        td(script.scriptFile)
                                                                    } else {
                                                                        td("inlineScript")
                                                                        td(script.inlineScript)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

new File('workflows.html').withWriter('utf-8') {
    it.writeLine writerWorkflow.toString()
}

StringWriter writerOthers = new StringWriter()
def buildOthers = new MarkupBuilder(writerOthers)

buildOthers.html {
    body {
        table(border: "1px", width: "100%") {
            tr {
                td("names")
                td("scripts")
            }
            jsonObject.output.listeners.each { listener ->
                tr {
                    // Если надо ссылку
                    //td("https://BaseUrl${listener.link}")
                    td("listener - ${listener.name}")
                    td {
                        table(border: 1) {
                            listener.scripts.each { script ->
                                tr {
                                    String value = ""
                                    if (listener.name == "Custom listener") {
                                        value = script.scriptCompileCtxOptions.toString()
                                    }
                                    if (script.scriptFile != null) {
                                        td("scriptFile ${value}")
                                        td(script.scriptFile)
                                    } else {
                                        td("inlineScript ${value}")
                                        td(script.inlineScript)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            jsonObject.output.fields.each { field ->
                tr {
                    // Если надо ссылку
                    //td("https://BaseUrl${field.link}")
                    td("field - ${field.name}")
                    td {
                        table(border: 1) {
                            field.scripts.each { script ->
                                tr {
                                    if (script.scriptFile != null) {
                                        td("scriptFile")
                                        td(script.scriptFile)
                                    } else {
                                        td("inlineScript")
                                        td(script.inlineScript)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            jsonObject.output.endpoints.each { endpoint ->
                tr {
                    // Если надо ссылку
                    //td("https://BaseUrl${endpoint.link}")
                    td("endpoint - ${endpoint.name}")
                    td {
                        table(border: 1) {
                            endpoint.scripts.each { script ->
                                tr {
                                    if (script.scriptFile != null) {
                                        td("scriptFile")
                                        td(script.scriptFile)
                                    } else {
                                        td("inlineScript")
                                        td(script.inlineScript)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            jsonObject.output.behaviours.each { behaviour ->
                tr {
                    // Если надо ссылку
                    //td("https://BaseUrl${behaviour.link}")
                    td("behaviour - ${behaviour.name}")
                    td {
                        table(border: 1) {
                            behaviour.scripts.each { script ->
                                tr {
                                    td(script.name)
                                    if (script.scriptFile != null) {
                                        td("scriptFile")
                                        td(script.scriptFile)
                                    } else {
                                        td("inlineScript")
                                        td(script.inlineScript)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            jsonObject.output.fragments.each { fragment ->
                tr {
                    // Если надо ссылку
                    //td("https://BaseUrl${fragment.link}")
                    td("fragment - ${fragment.name}")
                    td {
                        table(border: 1) {
                            fragment.scripts.each { script ->
                                tr {
                                    if (script.scriptFile != null) {
                                        td("scriptFile")
                                        td(script.scriptFile)
                                    } else { td("inlineScript")
                                        td(script.inlineScript)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            jsonObject.output.jobs.each { job ->
                tr {
                    // Если надо ссылку
                    //td("https://BaseUrl${job.link}")
                    td("job - ${job.name}")
                    td {
                        table(border: 1) {
                            job.scripts.each { script ->
                                tr {
                                    if (script.scriptFile != null) {
                                        td("scriptFile")
                                        td(script.scriptFile)
                                    } else {
                                        td("inlineScript")
                                        td(script.inlineScript)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

new File('others.html').withWriter('utf-8') {
    it.writeLine writerOthers.toString()
}