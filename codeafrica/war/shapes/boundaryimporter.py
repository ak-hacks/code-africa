"""
A python module to process country outlines from shapefiles
"""

import json

import shapefile

country_index = {}
country_records = {}

template = "%s:%s:%s"

def init():
	# build the country index
	for i, c in enumerate(sf.records()):
		country_index[c[4]] = i

def all_countries():
	with open("out.txt", "wb") as out:
		lines = []
		for k in country_index.keys():
			lines.append(process_country(k) + "\n")

		out.writelines(lines)

def process_country(name):
	index = country_index[name]
	shape = sf.shapes()[index]
	record = sf.records()[index]
	# Find the parts of the country
	parts = shape.parts

	# Find the biggest parts
	parts.append(len(shape.points) - 1)
	deltas = [parts[i] - parts[i-1] for i in xrange(1, len(parts))]

	starting_delta_index = deltas.index(max(deltas))

	start_index = parts[starting_delta_index]
	end_index = parts[starting_delta_index + 1]

	#points = [[round(x, 5), round(y,5)] for x, y in shape.points[start_index:end_index]]

	points = [[x[0], x[1]] for x in shape.points[start_index:end_index]]
	return template % (name, record[9:], points)

if __name__ == "__main__":
	pass