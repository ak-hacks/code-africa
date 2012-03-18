"""
A python module to process country outlines from shapefiles
"""

import shapefile

country_index = {}
sf = shapefile.Reader("shapefiles/TM_WORLD_BORDERS_SIMPL")

def init():
	# build the country index
	for i, c in enumerate(sf.records()):
		country_index[c[4]] = i

def process_country(name):
	index = country_index[name]
	shape = sf.shapes()[index]
	# Find the parts of the country
	parts = shape.parts

	# Find the biggest parts
	parts.append(len(shape.points) - 1)
	deltas = [parts[i] - parts[i-1] for i in xrange(1, len(parts))]

	starting_delta_index = deltas.index(max(deltas))

	start_index = parts[starting_delta_index]
	end_index = parts[starting_delta_index + 1]

	print shape.points[start_index:end_index]

if __name__ == "__main__":
	pass