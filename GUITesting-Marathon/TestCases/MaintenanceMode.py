#{{{ Marathon
from default import *
#}}} Marathon

def test():


    set_java_recorded_version("1.8.0_271")
    if window('My Java Http Server-[Stopped]'):
        click('Start server')
    close()

    if window('My Java Http Server-[Running]'):
        select('Switch to maintenance mode', 'true')
    close()

    if window('My Java Http Server-[Maintenance]'):
        assert_p('lbl:not running', 'Text', 'maintenance')
        assert_p('not running', 'Text', '192.168.100.61')
        assert_p('Server listening port', 'Text', '8081')
        assert_p('Stop server', 'Text', 'Stop server')
        assert_p('Stop server', 'Enabled', 'true')
        assert_p('Switch to maintenance mode', 'Enabled', 'true')
        assert_p('...', 'Enabled', 'true')
        assert_p('button', 'Enabled', 'false')
        select('Switch to maintenance mode', 'true')
    close()
    pass