#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_271")
    if window('My Java Http Server-[Stopped]'):
        select('Server listening on port', '')

        if window('Message'):
            click('OK')
        close()
    close()
    set_java_recorded_version("1.8.0_271")
    if window('My Java Http Server-[Stopped]'):
        select('Server listening on port', '')
        select('Server listening on port', '8188888')

        if window('Message'):
            click('OK')
        close()

        select('Server listening on port', '81a')

        if window('Message'):
            click('OK')
        close() 
        select('Server listening on port', '8081')
    close()

    pass