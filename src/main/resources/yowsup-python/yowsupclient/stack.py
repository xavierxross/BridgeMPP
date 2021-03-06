from yowsup.stacks import YowStack, YowStackBuilder
from yowsupclient.layer import BridgeMPPCliLayer
from yowsup.layers.auth import AuthError
from yowsup.layers import YowLayerEvent
from yowsup import env
from yowsup.env import S40YowsupEnv
import sys

class BridgeMPPCliStack(object):
    def __init__(self, credentials, encryptionEnabled = False):
        stackBuilder = YowStackBuilder()

        if not encryptionEnabled:
            env.CURRENT_ENV = S40YowsupEnv()

        self.stack = stackBuilder\
            .pushDefaultLayers(encryptionEnabled)\
            .push(BridgeMPPCliLayer)\
            .build()

        self.stack.setCredentials(credentials)

    def start(self):
        print("Yowsup Cli client\n==================\nType /help for available commands\n")
        self.stack.broadcastEvent(YowLayerEvent(BridgeMPPCliLayer.EVENT_START))

        try:
            self.stack.loop(timeout = 0.5, discrete = 0.5)
        except AuthError as e:
            print("Auth Error, reason %s" % e)
        except KeyboardInterrupt:
            print("\nYowsdown")
            sys.exit(0)
