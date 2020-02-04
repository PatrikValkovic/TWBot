package cz.valkovic.java.twbot.services.configuration;

import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ConfigurationImpl implements Configuration {

    private PublicConfiguration pubConf;
    private InterConfiguration interConf;

    @Inject
    public ConfigurationImpl(PublicConfiguration pubConf,
                             InterConfiguration interConf) {
        this.pubConf = pubConf;
        this.interConf = interConf;
    }

    //region public
    @Override
    public String userAgent() {
        return this.pubConf.userAgent();
    }

    @Override
    public int reloadPageMin() {
        return this.pubConf.reloadPageMin();
    }

    @Override
    public int reloadPageMax() {
        return this.pubConf.reloadPageMax();
    }

    @Override
    public int parseTime() {
        return this.pubConf.parseTime();
    }

    @Override
    public int navigationTimeMin() {
        return this.pubConf.navigationTimeMin();
    }

    @Override
    public int navigationTimeMax() {
        return this.pubConf.navigationTimeMax();
    }

    @Override
    public int windowWidth() {
        return this.pubConf.windowWidth();
    }

    @Override
    public int windowHeight() {
        return this.pubConf.windowHeight();
    }

    @Override
    public boolean maximalized() {
        return this.pubConf.maximalized();
    }

    @Override
    public boolean fullscreen() {
        return this.pubConf.fullscreen();
    }

    @Override
    public String username() {
        return this.pubConf.username();
    }

    @Override
    public String password() {
        return this.pubConf.password();
    }

    @Override
    public String serverName() {
        return this.pubConf.serverName();
    }

    @Override
    public String homepage() {
        return this.pubConf.homepage();
    }
    //endregion

    //region inter
    @Override
    public boolean firstRun() {
        return this.interConf.firstRun();
    }

    @Override
    public int majorVersion() {
        return 0;
    }

    @Override
    public int minorVersion() {
        return 1;
    }

    @Override
    public int patchVersion() {
        return 0;
    }

    @Override
    public String appDomainRegex() {
        return this.interConf.appDomainRegex();
    }

    @Override
    public String loginPageRegex() {
        return this.interConf.loginPageRegex();
    }

    @Override
    public String twstatsDomain() {
        return this.interConf.twstatsDomain();
    }

    @Override
    public int maxLockWaitingTime() {
        return this.interConf.maxLockWaitingTime();
    }







    @Override
    public String setProperty(String s, String s1) {
        return null;
    }

    @Override
    public String removeProperty(String s) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void load(InputStream inputStream) throws IOException {

    }

    @Override
    public void load(Reader reader) throws IOException {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public void addPropertyChangeListener(String s, PropertyChangeListener propertyChangeListener) {

    }





    @Override
    public void list(PrintStream printStream) {

    }

    @Override
    public void list(PrintWriter printWriter) {

    }

    @Override
    public void store(OutputStream outputStream, String s) throws IOException {

    }

    @Override
    public String getProperty(String s) {
        return null;
    }

    @Override
    public String getProperty(String s, String s1) {
        return null;
    }

    @Override
    public void storeToXML(OutputStream outputStream, String s) throws IOException {

    }

    @Override
    public Set<String> propertyNames() {
        return null;
    }
    //endregion
}
