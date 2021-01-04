#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_271")
    if window('My Java Http Server-[Stopped]'):
        select('Server listening on port', '8082')
        click('...')

        if window('Select root directory'):
            select('JFileChooser_0', '#C/jruby-parser')
        close()

        click('button')

        if window('Select maintenance page'):
            select('JFileChooser_0', '#C/README.txt')
        close()

        assert_p('Maintenance page', 'Text', 'C:\\Users\\User\\Downloads\\marathon\\README.txt')
        assert_p('Web root directory', 'Text', 'C:\\Users\\User\\Downloads\\marathon\\jruby-parser')
        assert_p('Server listening on port', 'Text', '8082')
        window_closed('My Java Http Server-[Stopped]')
    close()

    set_java_recorded_version("1.8.0_271")
    if window('My Java Http Server-[Stopped]'):
        rightclick('Server listening on port')
        assert_p('Web root directory', 'Text', 'C:\\Users\\User\\Downloads\\marathon\\jruby-parser')
        assert_p('Maintenance page', 'Text', 'C:\\Users\\User\\Downloads\\marathon\\README.txt')
    close()
    pass