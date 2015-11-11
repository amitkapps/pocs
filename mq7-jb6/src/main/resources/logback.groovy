def serverName = System.properties['server.name']

displayStatus()
initAppenders(serverName)
initLoggers()


def displayStatus() {
    statusListener OnConsoleStatusListener
}


def initAppenders(serverName) {
    def filePattern = "[%d{dd/MM/yyyy hh:mm:ss:sss z}] %t %5p %c{4}: %m%n"
    appender('applogfile', RollingFileAppender) {
        file = "/home/logs/applogs/mqrar/mq7-jb6.log"

        //rollingPolicy(TimeBasedFileNamingAndTriggeringPolicy) {
        rollingPolicy(TimeBasedRollingPolicy) {
            FileNamePattern = "hazard-%d{yyyy-MM}.zip"

            /*timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
              maxFileSize = "50MB"
            }*/
        }
        encoder(PatternLayoutEncoder) {
            pattern = filePattern
        }
    }
}


def initLoggers() {
    def appAppender = ['applogfile']
    logger 'arch.poc', DEBUG, appAppender, true

    logger 'org.springframework.boot', INFO, appAppender, true
    logger 'org.springframework.beans', INFO, appAppender, true
    logger 'org.springframework.core', INFO, appAppender, true
    logger 'org.springframework.context', INFO, appAppender, true
    logger 'org.springframework.web', INFO, appAppender, true
    logger 'org.springframework.web.mvc', INFO, appAppender, true
    logger 'org.springframework.hateoas', INFO, appAppender, true
    logger 'org.springframework.web.servlet', INFO, appAppender, true
    logger 'org.springframework', INFO, appAppender, true

    logger 'com.fasterxml.jackson.core', INFO, appAppender, true
    logger 'com.fasterxml.jackson.databind', INFO, appAppender, true

    root DEBUG, ['stdout']
}