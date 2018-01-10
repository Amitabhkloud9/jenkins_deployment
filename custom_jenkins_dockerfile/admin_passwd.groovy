import jenkins.model.*
import hudson.security.*

import jenkins.*
import hudson.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import com.cloudbees.plugins.credentials.impl.*
import hudson.plugins.sshslaves.*;
import hudson.model.*

def instance = Jenkins.getInstance()
def env = System.getenv()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)

hudsonRealm.createAccount("admin","admin@123")
instance.setSecurityRealm(hudsonRealm)
def strategy = new hudson.security.FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
instance.setAuthorizationStrategy(strategy)

instance.save()

String keyfile = "/home/pzmdop/.ssh/id_rsa"
global_domain = Domain.global()
credentials_store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()
credentials = new BasicSSHUserPrivateKey(CredentialsScope.GLOBAL, "jenkins", "jenkins", new BasicSSHUserPrivateKey.FileOnMasterPrivateKeySource(keyfile), "", "")
credentials_store.addCredentials(global_domain, credentials)
