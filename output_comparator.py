#!/Users/krisztianszenasi/.pyenv/shims/python
import json
from re import S
import sys, getopt 
import os
from os.path import isfile, join
import pathlib

class OutPutComparator:
    
    def _compare_files(self, output, expected):
        output_dict = self._read_and_format_file(output)
        expected_dict = self._read_and_format_file(expected)

        if(not(output_dict and expected_dict)):
            failed = True


        got_numbers = self._get_line_numbers_from_commands(output_dict)
        expected_numbers = self._get_line_numbers_from_commands(expected_dict)

        failed = False

        while got_numbers and expected_numbers:
            got_next, got_key, got_line = self._get_command_by_line_count(output_dict, got_numbers)
            expected_next, expected_key, expected_line = self._get_command_by_line_count(expected_dict, expected_numbers)

            if got_next != expected_next:
                self._print_error(got_next, expected_next, got_line, output)
                failed = True
                break

            if(self._handle_nested_dict(output_dict[got_key], expected_dict[expected_key], output, got_line)):
                failed = True
                break

        if(not failed):
            print(f"✅Test passed in {output}")
        return failed

    def _handle_nested_dict(self, output, expected, filename, line):
        output_keys = output.keys()
        expected_keys = expected.keys()

        got_out = output_keys - expected_keys
        expected_out = expected_keys - output_keys

        if(got_out or expected_out):
            self._print_error(got_out, expected_out, line, filename)
            return True

        for key in output:
            if(self._handle_nested_line(output[key], expected[key], filename)):
                return True
        return False
    
    def _handle_nested_line(self, output, expected, file_name):
        line = output['line']
        
        out_value = output['value']
        expected_value = expected['value']

        if(isinstance(out_value, list)):
            out_value.sort()

        if(isinstance(expected_value, list)):
            expected_value.sort()

        if(out_value != expected_value):
            self._print_error(out_value, expected_value, line, file_name)
            return True

        return False

    def _print_error(self, got, expected, line, output):
            print(f'\n❌Test failed in "{output}".')
            print(f'\tError at line {line}:')
            print(f'\t\tGot: "{got}"')
            print(f'\t\tExpected: "{expected}"\n')

    def _get_command_by_line_count(self, commands, numbers):
        for key in commands:
            command, line = key.split('-')
            if(int(line) == numbers[0]):
                numbers.pop(0)
                return command, key, line

    def _get_line_numbers_from_commands(self, commands):
        line_numbers = []
        try:
            for key in commands:
                line_numbers.append(int(key.split('-')[1]))

            line_numbers.sort()
        except TypeError:
            print("❌Error could not get lines numbers from commands.")
        return line_numbers

    def _read_and_format_file(self, filename):
        """Reads the file and puts it into a dictionary."""
        out = dict()
        try:
            file = open(filename, 'r')
        except FileNotFoundError:
            print(f"❌Error: Could not open file {filename}")

        lines = file.read().lower().split('\n')
        file.close()

        parent_key = ""
        line_counter = 0

        for line in lines:
            line_counter+=1
            # remove whitespaces check if not empty
            if(line := line.replace(' ', '')):
                if '#' != line[0]:
                    if(':' in line):
                        key, values = line.split(':')

                        if(not values):
                            parent_key = f'{key}-{line_counter}'
                            out[parent_key] = dict()
                        else:
                            if(',' in values):
                                values = values.split(',')
                            try:
                                out[parent_key][key] = {'value' : values}
                                out[parent_key][key]['line'] = line_counter
                            except KeyError:
                                print(f'🚧Error: could not parse output into json. File: {filename}')
                                return None
                    else:
                        try:
                            out[f'{line}-{line_counter}'] = dict()
                        except UnboundLocalError:
                            print(f'🚧Error: Line "{key}" in "{filename}" could not be parsed to JSON.')
        return out

    def _dump_json(self, filename):
        """Format output to json and save it."""
        dicionary = self._read_and_format_file(filename)
        dicionary['name'] = outname = filename.split('.')[0]
        print(json.dumps(dicionary, indent=4, sort_keys=True))

    def _run_tests(self, out_directory, expected_diretory):
        out_files = self._read_directory(out_directory)
        exp_files = self._read_directory(expected_diretory)

        successful = 0

        print("\nRunning tests:\n")
        for out in out_files:
            expected = out.replace('out', 'expected')

            if expected not in exp_files:
                print(f"\"{out}\" missing it's expected file \"{expected}\"")
                
            else:
                if(not self._compare_files(join(out_directory, out), join(expected_diretory, expected))):
                    successful += 1

        print(f'\n Testing ended. Passed {successful} out of {len(out_files)}.')

    def _read_directory(self, directory):
        files = []
        for file in os.listdir(directory):
            files.append(file)
        files.sort()
        return files

    def handle_input(self):
        output_directory = 'rcs/testoutputs'
        expected_directory = 'rcs/expectedoutputs'
        argv = sys.argv[1:]

        try:
            opts, args = getopt.getopt(argv, "o:e:")

        except:
            print("Error")

        for opt, arg in opts:
            if opt in ['-o']:
                output_directory = join(pathlib.Path().resolve(), arg)
            elif opt in ['-e']:
                expected_directory = join(pathlib.Path().resolve(), arg)


        self._run_tests(output_directory, expected_directory)

if __name__ == '__main__':
    comparator = OutPutComparator()
    comparator.handle_input()





