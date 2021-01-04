#{{{ Marathon
from default import *
#}}} Marathon

def test():
    set_java_recorded_version("1.8.0_271")
    if window('My Java Http Server-[Stopped]'):
        click('...')

        if window('Select root directory'):
            select('JFileChooser_0', '#C/support')
        close()

        assert_p('Web root directory', 'Text', 'C:\\Users\\User\\Downloads\\marathon\\support')
        click('button')

        if window('Select maintenance page'):
            select('JFileChooser_0', '#C/__init__.py')
        close()

        assert_p('Maintenance page', 'Text', 'C:\\Users\\User\\Downloads\\marathon\\__init__.py')
    close()
    pass