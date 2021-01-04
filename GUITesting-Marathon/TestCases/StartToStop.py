#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_271")
    if window('My Java Http Server-[Stopped]'):
        click('Start server')
    close()

    if window('My Java Http Server-[Running]'):
        click('Stop server')
    close()

    if window('My Java Http Server-[Stopped]'):
        assert_p('lbl:not running', 'Text', 'not running')
        assert_p('not running', 'Text', 'not running')
        assert_p('Server listening port', 'Text', 'not running')
        assert_p('Start server', 'Text', 'Start server')
        assert_p('Start server', 'Enabled', 'true')
        assert_p('Switch to maintenance mode', 'Enabled', 'false')
        assert_p('...', 'Enabled', 'true')
        assert_p('button', 'Enabled', 'true')
        assert_p('Web root directory', 'Enabled', 'true')
        assert_p('Maintenance page', 'Enabled', 'true')
    close()

    pass