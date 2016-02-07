package nl.unionsoft.common.server;

import org.apache.commons.lang.StringUtils;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(ServerServiceImpl.class);
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8880;
    private String host;
    private Integer port;
    private final String contextPath;
    private String overrideDescriptor;
    private String war;

    private boolean runOnStartup;
    private Server server;

    public ServerServiceImpl (String contextPath) {
        this.contextPath = contextPath;
        war = "./src/main/webapp";
    }

    private void init() {
        if (server == null) {

            // Start Jetty (And Jersey)
            server = new Server();
            {
                // SocketConnector
                final SocketConnector connector = new SocketConnector();
                connector.setPort(getPort());
                server.addConnector(connector);
            }
            {
                final WebAppContext webappcontext = new WebAppContext();
                if (StringUtils.isNotEmpty(overrideDescriptor)) {
                    webappcontext.setOverrideDescriptor(overrideDescriptor);
                }

                webappcontext.setContextPath(contextPath);
                webappcontext.setWar(war);

                final HandlerCollection handlers = new HandlerCollection();
                handlers.setHandlers(new Handler[] { webappcontext, new DefaultHandler() });
                server.setHandler(handlers);

            }
        }
    }

    public boolean startServer() {
        init();
        boolean result = false;
        try {
            if (!server.isRunning()) {
                server.start();
            }
            result = true;
        } catch(final Exception e) {
            LOG.error("error starting server", e);
        }
        return result;
    }

    public boolean stopServer() {
        boolean result = false;
        try {
            if (server.isRunning()) {
                server.stop();
            }
            result = true;
        } catch(final Exception e) {
            LOG.error("error stopping server", e);
        }
        return result;
    }

    public boolean restartServer() {
        boolean result = stopServer();
        if (result) {
            result = startServer();
        }
        return result;
    }

    public boolean getRunOnStartup() {
        return runOnStartup;

    }

    public void setRunOnStartup(final boolean runOnStartup) {
        this.runOnStartup = runOnStartup;
    }

    public boolean isServerRunning() {
        return server.isRunning();
    }

    public String getServerPath() {
        return "http://" + getHost() + ":" + getPort() + "/";
    }

    /**
     * @return the host
     */
    public String getHost() {
        if (StringUtils.isBlank(host)) {
            host = DEFAULT_HOST;
        }
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(final String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        if (port == null) {
            port = DEFAULT_PORT;
        }
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(final Integer port) {
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getOverrideDescriptor() {
        return overrideDescriptor;
    }

    public String getWar() {
        return war;
    }

    public void setOverrideDescriptor(String overrideDescriptor) {
        this.overrideDescriptor = overrideDescriptor;
    }

    public void setWar(String war) {
        this.war = war;
    }

}
