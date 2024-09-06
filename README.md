# brunt --- a conexp-clj based command line tool for computing formal concepts and more

A command line tool for some functions of the [conexp-clj](https://github.com/tomhanika/conexp-clj) tool for Formal Concept Analysis.

## Usage

The standalone jar (in builds/uberjar) can be used via:

    $ java -jar brunt-0.1.0-standalone.jar [args]

The following options may be used:

```
-f, --function FUNCTION 	Specifies Function to be executed.
-h, --help 			Displays Intruction Texts.
```

All supported Functions, as well as their necessary arguments can be displayed by executing:

    $ java -jar brunt-0.1.0-standalone.jar -h

The Arguments of a specific function can be display with:

    $ java -jar brunt-0.1.0-standalone.jar -h [function]

## Example

There is an example context given in the testing-data folder. To draw an image of a concept lattice, the following command can be used (directly from the fca-clj directory):

    $ java -jar builds/uberjar/brunt-0.1.0-standalone.jar -f draw-concept-lattice testing-data/living-beings-and-water.ctx

Note that the file path from the current directory needs to be specified to access the jar file.

## Arguments

Arguments such as sets need to be specified in string form, by encasing them in the " character.
A set of integers may be input like this:

    $ "#{1 2 3}"

In case strings are provided as entries to a set, they need to be encases as well. For this to be interpreted correctly, the inner " need to be escaped using the \ character:

    $ "#{\"a\" \"b\" \"c\"}"


## Detailed instruction can be found here:

[Formal Contexts](doc/Formal-Contexts.org)   
[Concept Lattices](doc/Concept-Lattices.org)   
[Implications](doc/Implications.org)   
[Exploration](doc/Exploration.org)   
[pq Cores](doc/pqcores.org)   


## License

Copyright © 2024 Jana Fischer, Tom Hanika, Johannes Hirth, Jannik Nordmeyer

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
